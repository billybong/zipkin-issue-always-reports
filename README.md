For issue: https://github.com/spring-cloud/spring-cloud-sleuth/issues/224

curl localhost:8080/ and see that the second endpoint gets the forwarded X-Span* headers.
The SpanReporter is also triggered which is seen in the log "Should have reported span"