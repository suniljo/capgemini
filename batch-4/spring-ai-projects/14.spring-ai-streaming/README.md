# Streaming OpenAI App

Spring Boot **4.1.0** + **Spring AI** app that streams OpenAI chat completions
to the client **incrementally**, as tokens are generated, over Server-Sent
Events (SSE) — instead of waiting for the full response and sending it back
in one shot.

## Stack

- Spring Boot 4.1.0
- Spring WebFlux (reactive, needed for streaming responses)
- Spring AI (`spring-ai-starter-model-openai`, using `ChatClient.stream()`)
- Java 21, Maven

## How the streaming actually works

1. `StreamingChatService.streamResponse(prompt)` calls
   `chatClient.prompt().user(prompt).stream().content()`, which returns a
   `Flux<String>` — Spring AI subscribes to the OpenAI streaming API
   internally and emits each incremental text chunk as it arrives from the
   model (this mirrors OpenAI's own `stream: true` SSE chunks).
2. `StreamingChatController` wraps each chunk in a `ServerSentEvent` and
   returns `Flux<ServerSentEvent<String>>` with
   `produces = MediaType.TEXT_EVENT_STREAM_VALUE`. WebFlux flushes each
   element to the client as soon as it's emitted — nothing is buffered
   server-side waiting for the full answer.
3. A final `complete` event is appended after the content flux finishes, so
   the client has an explicit signal to stop listening (rather than relying
   only on the HTTP connection closing).
4. The bundled demo page (`src/main/resources/static/index.html`) uses the
   browser's native `EventSource` API to consume `GET /api/chat/stream` and
   appends each chunk to the page as it arrives, so the response visibly
   "types itself out" in real time.

## 1. Prerequisites

- JDK 21+
- Maven 3.9+
- An OpenAI API key

## 2. Configure

```bash
export OPENAI_API_KEY=sk-...
```

Model/temperature are set in `src/main/resources/application.yml` under
`spring.ai.openai.chat.options` — change `model` there (e.g. `gpt-4o`,
`gpt-4o-mini`) as needed.

## 3. Run

```bash
mvn spring-boot:run
```

Then open **http://localhost:8080** in a browser — type a prompt and hit
Send to watch the response stream in incrementally.

## 4. API

### GET /api/chat/stream?prompt=... (SSE, for `EventSource`)

```
GET /api/chat/stream?prompt=Explain%20quantum%20computing%20simply
Accept: text/event-stream
```

Streamed response (each `data:` line is one incremental chunk):

```
event: message
data: Quantum

event: message
data:  computing

event: message
data:  uses

...

event: complete
data:
```

### POST /api/chat/stream (SSE, for `fetch()` + stream reader — `EventSource` can't POST)

```
POST /api/chat/stream
Content-Type: application/json
Accept: text/event-stream

{ "prompt": "Explain quantum computing simply" }
```

Example client-side consumption with `fetch` (useful if you need POST, custom
headers, or auth tokens that `EventSource` doesn't support):

```javascript
const res = await fetch('/api/chat/stream', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({ prompt: 'Explain quantum computing simply' })
});
const reader = res.body.getReader();
const decoder = new TextDecoder();
let done = false;
while (!done) {
  const { value, done: streamDone } = await reader.read();
  done = streamDone;
  if (value) console.log(decoder.decode(value));
}
```

## Notes / things to double check before shipping

- **Spring AI version pin**: `spring-ai.version` in the pom is a placeholder
  (`1.1.0`). Confirm the release compatible with Spring Boot 4.1.0 / Spring
  Framework 7 at https://docs.spring.io/spring-ai/reference/ before building.
- **Proxy buffering**: if you deploy behind Nginx or another reverse proxy,
  disable response buffering (`proxy_buffering off;` for Nginx) or the proxy
  will collect the whole stream before forwarding it, defeating the point of
  streaming.
- **CORS**: `WebConfig` currently allows all origins on `/api/**` for easy
  local testing from a separately-hosted frontend. Restrict this to known
  origins before deploying.
- **No persistence**: this app is intentionally focused on the streaming
  mechanics and doesn't store prompts/responses. If you want that too, it's
  straightforward to add a reactive repository (e.g. R2DBC) that writes the
  full assembled response once the Flux completes, similar to the earlier
  MySQL-backed prompt/response app.
- **Backpressure/cancellation**: if the client disconnects mid-stream (closes
  the tab, calls `eventSource.close()`), WebFlux cancels the upstream
  subscription, which Spring AI propagates to stop pulling further tokens
  from OpenAI — you're not billed for tokens generated after cancellation
  reaches the provider.
