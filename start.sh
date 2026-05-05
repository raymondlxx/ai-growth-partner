#!/bin/bash
# AI Growth Partner — 一键启动所有服务
# 用法: ./start.sh
# 自定义前端端口: FRONTEND_PORT=3002 ./start.sh
# 需要: MySQL (localhost:3306), Redis (localhost:6379), MongoDB (localhost:27017) 已运行

set -e

PROJECT_ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
BACKEND_DIR="$PROJECT_ROOT/backend"
FRONTEND_DIR="$PROJECT_ROOT/frontend"

FRONTEND_PORT=${FRONTEND_PORT:-3002}

SERVICES=(
  "user-service:8080"
  "task-service:8081"
  "path-service:8082"
  "content-service:8083"
  "social-service:8083"
  "ai-gateway:8084"
)

echo "============================================"
echo "  AI Growth Partner — Starting All Services"
echo "============================================"

# ---- 检查中间件 ---------------------------------------------------
echo ""
echo "[1/4] Checking middleware (MySQL, Redis, MongoDB)..."

check_port() {
    local port=$1
    local name=$2
    if lsof -i:$port >/dev/null 2>&1; then
        echo "  ✓ $name (port $port) is up"
    else
        echo "  ✗ $name (port $port) is NOT running — please start it first"
        echo "    MySQL:   brew services start mysql"
        echo "    Redis:   brew services start redis"
        echo "    MongoDB: brew services start mongodb-community"
        return 1
    fi
}

check_port 3306 "MySQL"    || exit 1
check_port 6379 "Redis"    || exit 1
check_port 27017 "MongoDB" || exit 1

# ---- 清理旧进程 ---------------------------------------------------
echo ""
echo "[2/4] Stopping old instances..."

for entry in "${SERVICES[@]}"; do
    svc="${entry%%:*}"
    pkill -f "${svc}.*1.0.0-SNAPSHOT.jar" 2>/dev/null || true
done
sleep 2

for entry in "${SERVICES[@]}"; do
    port="${entry##*:}"
    if lsof -ti:$port >/dev/null 2>&1; then
        echo "  Force-clearing port $port..."
        lsof -ti:$port | xargs kill -9 2>/dev/null || true
    fi
done
sleep 1

# ---- 编译后端 ----------------------------------------------------
echo ""
echo "[3/4] Building backend..."
cd "$BACKEND_DIR"
mvn package -DskipTests -q
if [ $? -ne 0 ]; then
    echo "  ✗ Maven build failed"
    exit 1
fi
echo "  ✓ Backend built"

# ---- 启动后端服务 ------------------------------------------------
echo ""
echo "[4/4] Starting backend services..."

mkdir -p "$BACKEND_DIR"/{user-service,task-service,path-service,content-service,social-service,ai-gateway}/logs 2>/dev/null || true

start_service() {
    local svc=$1
    local port=$2
    local dir="$BACKEND_DIR/$svc"

    if [ ! -d "$dir" ]; then
        echo "  ✗ $svc: directory not found"
        return
    fi

    (
        if [ -f "$dir/.env" ]; then
            set -a
            source "$dir/.env"
            set +a
        fi
        cd "$dir"
        java -jar "target/${svc}-1.0.0-SNAPSHOT.jar" \
            --spring.config.location=src/main/resources/application.yml \
            >> "logs/${svc}.log" 2>&1 &
    )

    echo "  ✓ $svc started (port $port, PID $!)"
}

for entry in "${SERVICES[@]}"; do
    svc="${entry%%:*}"
    port="${entry##*:}"
    start_service "$svc" "$port"
done

# ---- 等待服务就绪 -------------------------------------------------
echo ""
echo "Waiting for services to initialize..."
sleep 8

echo ""
echo "Health checks:"
for entry in "${SERVICES[@]}"; do
    svc="${entry%%:*}"
    port="${entry##*:}"
    if curl -s -f -m 3 "http://localhost:$port/actuator/health" >/dev/null 2>&1; then
        echo "  ✓ $svc (port $port)"
    elif curl -s -m 3 "http://localhost:$port/api/health" >/dev/null 2>&1; then
        echo "  ✓ $svc (port $port)"
    else
        echo "  ? $svc (port $port) — may still be starting"
    fi
done

# ---- 前端 --------------------------------------------------------
echo ""
if [ -d "$FRONTEND_DIR" ] && [ -f "$FRONTEND_DIR/package.json" ]; then
    echo "Starting frontend (Next.js on port $FRONTEND_PORT)..."
    cd "$FRONTEND_DIR"
    if ! curl -s -m 2 http://localhost:$FRONTEND_PORT >/dev/null 2>&1; then
        PORT=$FRONTEND_PORT npm run dev >> "$BACKEND_DIR/frontend.log" 2>&1 &
        echo "  ✓ Frontend started (port $FRONTEND_PORT)"
    else
        echo "  ✓ Frontend already running on port $FRONTEND_PORT"
    fi
else
    echo "  (Frontend not found, skipping)"
fi

echo ""
echo "============================================"
echo "  All services started!"
echo "  Backend:  http://localhost:8080-8084"
echo "  Frontend: http://localhost:$FRONTEND_PORT"
echo "  Logs:     $BACKEND_DIR/*/logs/"
echo "============================================"
