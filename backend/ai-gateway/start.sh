#!/bin/bash
# AI Gateway 启动脚本
# 自动从 .env 加载环境变量，然后启动服务
# 用法: ./start.sh

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

# 检查 .env 文件
if [ -f ".env" ]; then
    echo "加载 .env 配置..."
    set -a
    source .env
    set +a
else
    echo "警告: .env 文件不存在，使用默认配置（可能为 Mock 模式）"
fi

echo "启动 ai-gateway (端口 8084)..."
echo "AI Provider: ${AI_PROVIDER:-mock}"
echo "Model: ${MINIMAX_MODEL:-minimax-01-16k}"

java -jar target/ai-gateway-1.0.0-SNAPSHOT.jar \
    --spring.config.location=src/main/resources/application.yml \
    "$@"
