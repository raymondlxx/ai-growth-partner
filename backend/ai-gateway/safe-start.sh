#!/bin/bash
# AI Gateway 安全启动脚本
# 启动前检查并杀掉已有实例，避免端口冲突
# 用法: ./safe-start.sh

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

JAR_FILE="target/ai-gateway-1.0.0-SNAPSHOT.jar"
PID_FILE=".ai-gateway.pid"

# 杀掉已有实例
if [ -f "$PID_FILE" ]; then
    OLD_PID=$(cat "$PID_FILE")
    if kill -0 "$OLD_PID" 2>/dev/null; then
        echo "Stopping existing instance PID $OLD_PID..."
        kill "$OLD_PID"
        sleep 2
        # 如果还没停，强制杀掉
        if kill -0 "$OLD_PID" 2>/dev/null; then
            kill -9 "$OLD_PID" 2>/dev/null || true
            sleep 1
        fi
    fi
fi

# 检查端口占用
if lsof -ti:8084 >/dev/null 2>&1; then
    echo "Port 8084 occupied, killing..."
    lsof -ti:8084 | xargs kill -9 2>/dev/null || true
    sleep 1
fi

# 加载 .env
if [ -f ".env" ]; then
    echo "Loading .env..."
    set -a
    source .env
    set +a
fi

echo "Starting ai-gateway..."
java -jar "$JAR_FILE" --spring.config.location=src/main/resources/application.yml &

# 记录新 PID
echo $! > "$PID_FILE"
echo "Started PID $(cat $PID_FILE)"

sleep 3

# 验证启动成功
if curl -s -m 3 http://localhost:8084/api/ai/chat \
     -X POST -H "Content-Type: application/json" \
     -d '{"userId":"health","message":"ping","history":[]}' \
     > /dev/null 2>&1; then
    echo "ai-gateway is up and running on port 8084"
else
    echo "WARNING: ai-gateway may not have started correctly"
fi
