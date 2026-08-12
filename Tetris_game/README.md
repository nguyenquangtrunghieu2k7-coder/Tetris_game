<div align="center">

<img src="https://api.iconify.design/lucide/gamepad-2.svg?color=%23FF5722" width="80" height="80" alt="Modern Tetris Java" />

# 🧱 TETRIS FROM ZERO: THE MODERN WAY

### Xây dựng Game Tetris chuẩn thi đấu quốc tế (Guideline) từ con số 0 với Java Swing

Dự án này là một bộ khung hoàn chỉnh giúp hiểu rõ các thuật toán nâng cao như **SRS (Super Rotation System), 7-Bag Randomizer, DAS/ARR** và quản lý **Game Loop** mà không dùng bất kỳ game engine nào.

[![Java](https://img.shields.io/badge/Java-8%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Swing](https://img.shields.io/badge/GUI-Java%20Swing-007396?style=for-the-badge&logo=java&logoColor=white)]()
[![SRS](https://img.shields.io/badge/Rotation-SRS%20Wall%20Kicks-4CAF50?style=for-the-badge)]()

</div>

---

## 🌟 Tổng quan
Một tựa game Tetris cơ bản chỉ là một ma trận 2D và các khối rơi xuống. Nhưng để game đạt chuẩn "Modern Tetris", nó cần một hệ thống logic đồ sộ phía sau. Project này chia tách rõ ràng giữa **Core Logic (Xử lý toán học)** và **Render Engine (Vẽ đồ họa)**.

## 🏗️ Kiến trúc hệ thống
Project tuân thủ chặt chẽ nguyên lý **Separation of Concerns**. Luồng đi của dữ liệu được mô tả như sau:

```mermaid
flowchart TB
    subgraph ENGINE ["Game Engine (Luồng điều khiển)"]
        direction TB
        TIMER["Swing Timer (60 FPS)"] --> INPUT["Input Manager<br/>(DAS / ARR)"]
        TIMER --> GRAVITY["Gravity Fall"]
        TIMER --> LOCK["Lock Delay System"]
    end

    subgraph LOGIC ["Core Logic (Trạng thái)"]
        direction LR
        BAG["7-Bag<br/>Randomizer"] --> QUEUE["Queue (Next)"]
        QUEUE --> BOARD["Ma trận 23x10"]
        BOARD <--> KICKS["SRS Wall Kicks"]
        BOARD --> CLEAR["Clear Lines"]
    end

    subgraph UI ["Giao diện (EDT Thread)"]
        direction LR
        KEY["Key Listener"] --> ACTION["Player Actions"]
        RENDER["Java 2D API"] --> SCREEN["Màn hình"]
    end

    ACTION -. Gọi lệnh .-> ENGINE
    ENGINE ==> LOGIC
    LOGIC -. Cập nhật UI .-> RENDER