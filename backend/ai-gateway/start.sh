#!/bin/bash
# AI Gateway 启动脚本
# 自动从 .env 加载环境变量，然后启动服务
# 如果端口 8084 被占用，自动清理后启动
# 用法: ./start.sh

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

# 如果端口被占用，先清理
if lsof -ti:8084 >/dev/null 2>&1; then
    echo "Port 8084 in use — clearing..."
    lsof -ti:8084 | xargs kill -9 2>/dev/null || true
    sleep 1
fi

# 检查 .env 文件
if [ -f ".env" ]; then
    echo "Loading .env..."
    set -a
    source .env
    set +a
else
    echo "Warning: .env not found, using default config (may run in mock mode)"
fi

echo "Starting ai-gateway (port 8084)..."
echo "  AI Provider: ${AI_PROVIDER:-mock}"
echo "  Model: ${MINIMAX_MODEL:-minimax-01-16k}"

java -jar target/ai-gateway-1.0.0-SNAPSHOT.jar \
    --spring.config.location=src/main/resources/application.yml \
    "$@"
