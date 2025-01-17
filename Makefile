DEMO_IMAGE_NAME=java-wasm-demo

.PHONY: build-go
build-go:
	@echo "building go plugin..."
	@docker run  -v ${PWD}/go-plugin:/app tinygo/tinygo:0.35.0 tinygo build -target wasi -o /app/main.wasm /app/main.go

.PHONY: build-java
build-java: build-go
	@echo "building java app image..."
	@cp go-plugin/main.wasm src/main/resources/plugin.wasm
	@docker build -t $(DEMO_IMAGE_NAME) .

.PHONY: run-java
run-java: build-java
	@echo "running java app image..."
	@docker run --rm $(DEMO_IMAGE_NAME)

.PHONY: clean
clean:
	@docker rmi $(DEMO_IMAGE_NAME)

.PHONY: demo
demo:
	@echo "Running the demo..."

.PHONY: all
all:
	@echo "For demo use demo target"

