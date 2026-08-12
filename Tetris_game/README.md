<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modern Tetris From Zero</title>
    <style>
        :root {
            --bg-color: #0f111a;
            --surface-color: #1e2130;
            --text-primary: #e2e8f0;
            --text-secondary: #94a3b8;
            --accent-color: #ff5722;
            --border-color: #334155;
            --code-bg: #0b0f19;
        }

        * {
            box-sizing: border-box;
            margin: 0;
            padding: 0;
        }

        body {
            font-family: 'Segoe UI', system-ui, -apple-system, sans-serif;
            background-color: var(--bg-color);
            color: var(--text-primary);
            line-height: 1.7;
            padding: 20px;
        }

        .container {
            max-width: 900px;
            margin: 0 auto;
        }

        /* Hero Section */
        .hero {
            text-align: center;
            padding: 60px 20px 40px;
            border-bottom: 1px solid var(--border-color);
            margin-bottom: 40px;
        }

        .hero img.icon {
            width: 80px;
            height: 80px;
            margin-bottom: 20px;
            filter: drop-shadow(0 0 10px rgba(255, 87, 34, 0.5));
        }

        .hero h1 {
            font-size: 2.5rem;
            font-weight: 800;
            color: #fff;
            margin-bottom: 15px;
            letter-spacing: 1px;
        }

        .hero p {
            font-size: 1.1rem;
            color: var(--text-secondary);
            max-width: 700px;
            margin: 0 auto 25px;
        }

        .badges {
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
            gap: 10px;
            margin-bottom: 25px;
        }

        .nav-links {
            display: flex;
            justify-content: center;
            gap: 15px;
            flex-wrap: wrap;
        }

        .nav-links a {
            color: var(--accent-color);
            text-decoration: none;
            font-weight: 600;
            padding: 5px 10px;
            border-radius: 5px;
            transition: background 0.2s;
        }

        .nav-links a:hover {
            background-color: rgba(255, 87, 34, 0.1);
        }

        /* Section Cards */
        section {
            background-color: var(--surface-color);
            border: 1px solid var(--border-color);
            border-radius: 12px;
            padding: 30px;
            margin-bottom: 30px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            transition: transform 0.2s, box-shadow 0.2s;
        }

        section:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 15px rgba(0, 0, 0, 0.2);
        }

        h2 {
            font-size: 1.8rem;
            color: #fff;
            margin-bottom: 20px;
            display: flex;
            align-items: center;
            gap: 10px;
        }

        h3 {
            font-size: 1.3rem;
            color: #fff;
            margin: 25px 0 15px;
        }

        p {
            margin-bottom: 15px;
        }

        /* Tables */
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            background-color: var(--code-bg);
            border-radius: 8px;
            overflow: hidden;
        }

        th, td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid var(--border-color);
        }

        th {
            background-color: rgba(255, 255, 255, 0.05);
            font-weight: 600;
            color: var(--accent-color);
        }

        tr:last-child td {
            border-bottom: none;
        }

        /* Code Blocks */
        pre {
            background-color: var(--code-bg);
            padding: 15px;
            border-radius: 8px;
            overflow-x: auto;
            border: 1px solid var(--border-color);
            margin: 15px 0;
        }

        code {
            font-family: 'Consolas', 'Monaco', monospace;
            font-size: 0.9rem;
            color: #a6accd;
        }

        p code, li code, td code {
            background-color: var(--code-bg);
            padding: 3px 6px;
            border-radius: 4px;
            color: #82aaff;
        }

        /* Lists */
        ul {
            margin-left: 20px;
            margin-bottom: 15px;
        }

        li {
            margin-bottom: 8px;
        }

        /* Mermaid Diagram Container */
        .mermaid {
            background-color: var(--code-bg);
            padding: 20px;
            border-radius: 8px;
            display: flex;
            justify-content: center;
            overflow-x: auto;
            margin-bottom: 20px;
        }

        /* Footer */
        footer {
            text-align: center;
            padding: 40px 20px;
            color: var(--text-secondary);
            font-size: 0.95rem;
        }
    </style>
</head>
<body>

    <div class="container">
        <!-- HEADER -->
        <header class="hero">
            <img src="https://api.iconify.design/lucide/gamepad-2.svg?color=%23FF5722" alt="Tetris Icon" class="icon">
            <h1>🧱 TETRIS FROM ZERO: THE MODERN WAY</h1>
            <p>Xây dựng Game Tetris chuẩn thi đấu quốc tế (Guideline) từ con số 0 với Java Swing. Dự án phân tách rõ ràng Core Logic và Render Engine, không phụ thuộc framework ngoài.</p>
            
            <div class="badges">
                <img src="https://img.shields.io/badge/Java-8%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java">
                <img src="https://img.shields.io/badge/GUI-Java%20Swing-007396?style=for-the-badge&logo=java&logoColor=white" alt="Swing">
                <img src="https://img.shields.io/badge/Rotation-SRS%20Wall%20Kicks-4CAF50?style=for-the-badge" alt="SRS">
            </div>

            <div class="nav-links">
                <a href="#tong-quan">🌟 Tổng quan</a>
                <a href="#kien-truc">🏗️ Kiến trúc</a>
                <a href="#giai-ngo">🧠 Giải ngố Logic</a>
                <a href="#bat-dau">🚀 Bắt đầu nhanh</a>
                <a href="#dieu-khien">🎮 Điều khiển</a>
                <a href="#xu-ly-loi">🛠️ Xử lý lỗi</a>
            </div>
        </header>

        <!-- SECTION: TỔNG QUAN -->
        <section id="tong-quan">
            <h2>🌟 Tổng quan</h2>
            <p>Một tựa game Tetris cơ bản chỉ là một ma trận 2D và các khối rơi xuống. Nhưng để game đạt chuẩn "Modern Tetris" (giống như Tetris Effect hay TETR.IO), nó cần một hệ thống logic đồ sộ phía sau. Project này chia tách rõ ràng giữa <strong>Core Logic (Xử lý toán học)</strong> và <strong>Render Engine (Vẽ đồ họa)</strong>.</p>
            
            <h3>Điểm nổi bật đáng tự hào</h3>
            <table>
                <thead>
                    <tr>
                        <th>Tính năng</th>
                        <th>Giải thích cho người mới</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><strong>Wall Kicks (SRS)</strong></td>
                        <td>Khi bạn xoay khối sát tường, thay vì bị kẹt (lỗi), thuật toán sẽ thử dịch chuyển khối (kick) để nó tự lách vào khoảng trống một cách ma thuật. Tích hợp cả xoay 180 độ!</td>
                    </tr>
                    <tr>
                        <td><strong>7-Bag Randomizer</strong></td>
                        <td>Gom 7 khối (I,O,T,S,Z,L,J) vào một "túi", xáo trộn rồi rút từ từ. Đảm bảo bạn không bao giờ xui xẻo nhận 4 khối Z liên tiếp.</td>
                    </tr>
                    <tr>
                        <td><strong>DAS / ARR</strong></td>
                        <td>Khi đè phím mũi tên, khối trượt vèo vèo sang ngang mượt mà không bị giật cục do độ trễ của hệ điều hành.</td>
                    </tr>
                    <tr>
                        <td><strong>Lock Delay</strong></td>
                        <td>Khi khối chạm đáy, nó không bị khóa cứng ngay. Bạn có thêm 0.5s để xoay và trượt nó vào các khe hẹp.</td>
                    </tr>
                </tbody>
            </table>
        </section>

        <!-- SECTION: KIẾN TRÚC -->
        <section id="kien-truc">
            <h2>🏗️ Kiến trúc hệ thống</h2>
            <p>Project tuân thủ chặt chẽ nguyên lý <strong>Separation of Concerns</strong>. Luồng đi của dữ liệu được mô tả như sau:</p>
            
            <div class="mermaid">
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
            </div>

            <table>
                <thead>
                    <tr>
                        <th>Module</th>
                        <th>Trách nhiệm cốt lõi</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><code>GameEngine.java</code></td>
                        <td>Đếm nhịp (Timer 60FPS), tính toán DAS/ARR, Lock Delay và trọng lực. Nhận input thô và chuyển thành hành động.</td>
                    </tr>
                    <tr>
                        <td><code>Game.java</code></td>
                        <td>Quản lý điểm số, tính năng Hold (Cất khối), sinh khối mới (Spawn), Hard Drop và Game Over.</td>
                    </tr>
                    <tr>
                        <td><code>Board.java</code></td>
                        <td>Ma trận lưới. Kiểm tra va chạm (<code>isValid</code>), đóng băng khối và xử lý dịch chuyển khi xóa hàng.</td>
                    </tr>
                    <tr>
                        <td><code>RotationSystem.java</code></td>
                        <td>Từ điển Wall Kicks. Tra bảng offset xem nếu xoay khối sát tường thì phải dịch đi bao nhiêu pixel.</td>
                    </tr>
                </tbody>
            </table>
        </section>

        <!-- SECTION: GIẢI NGỐ -->
        <section id="giai-ngo">
            <h2>🧠 Giải ngố: Các cơ chế hoạt động ra sao?</h2>
            
            <h3>1. Xoay ma trận bằng Toán học</h3>
            <p>Thay vì hard-code 4 hình dạng, dùng thuật toán xoay ma trận vuông 90 độ. Xoay thuận chiều (CW): Lấy ma trận chuyển vị (Transpose) sau đó đảo ngược thứ tự các cột.</p>
            
            <h3>2. Xóa hàng siêu tốc</h3>
            <p>Khi phát hiện hàng <code>i</code> đầy, ta dùng vòng lặp kéo toàn bộ các hàng phía trên tụt xuống 1 bậc. Quan trọng nhất là tăng <code>i++</code> để vòng lặp kế tiếp quét lại chính hàng vừa tụt xuống.</p>
            
            <h3>3. Chống lỗi Dội phím (Key Repeat)</h3>
            <p>Hệ điều hành spam sự kiện <code>KeyPressed</code> liên tục khi đè phím. Khắc phục bằng cờ (Flags) <code>spaceHeld = true</code> và chỉ mở khóa khi <code>keyReleased</code> kích hoạt.</p>
        </section>

        <!-- SECTION: BẮT ĐẦU -->
        <section id="bat-dau">
            <h2>🚀 Bắt đầu nhanh (Step-by-step)</h2>
            <p>Yêu cầu: Máy tính cài sẵn Java JDK 8 trở lên.</p>
            
            <pre><code># 1. Clone project về máy
git clone https://github.com/your-username/Modern-Tetris-Java.git
cd Modern-Tetris-Java

# 2. Biên dịch (Compile)
javac *.java

# 3. Chạy Game (Run)
java Main</code></pre>
        </section>

        <!-- SECTION: ĐIỀU KHIỂN -->
        <section id="dieu-khien">
            <h2>🎮 Hướng dẫn điều khiển (Pro Controls)</h2>
            <table>
                <thead>
                    <tr>
                        <th>Phím tắt</th>
                        <th>Tính năng</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><code>🡄</code> / <code>🡆</code></td>
                        <td>Di chuyển trái/phải (Đè lâu để lướt DAS)</td>
                    </tr>
                    <tr>
                        <td><code>🡇</code> (Xuống)</td>
                        <td>Soft Drop (Rơi nhanh)</td>
                    </tr>
                    <tr>
                        <td><code>Space</code></td>
                        <td><strong>Hard Drop</strong> (Đập khối xuống đáy lập tức)</td>
                    </tr>
                    <tr>
                        <td><code>🡅</code> / <code>X</code></td>
                        <td>Xoay phải (CW)</td>
                    </tr>
                    <tr>
                        <td><code>Z</code></td>
                        <td>Xoay trái (CCW)</td>
                    </tr>
                    <tr>
                        <td><code>C</code> / <code>Shift</code></td>
                        <td>Hold Piece (Cất khối)</td>
                    </tr>
                </tbody>
            </table>
        </section>

        <!-- SECTION: XỬ LÝ LỖI -->
        <section id="xu-ly-loi">
            <h2>🛠️ Xử lý lỗi thường gặp</h2>
            <table>
                <thead>
                    <tr>
                        <th>Báo lỗi Console</th>
                        <th>Cách Khắc Phục</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><code>NullPointerException: "this.offsets" is null</code></td>
                        <td>Quên gọi hàm <code>updateOffsets();</code> ở dòng cuối cùng trong Constructor của các class Piece (OPiece, IPiece...). Thêm vào rồi compile lại.</td>
                    </tr>
                    <tr>
                        <td>Game khựng, thả block không rơi</td>
                        <td>Hàm tìm offset bị hard-code. Mở <code>Piece.java</code>, đổi <code>if (shape[r][c] == 1)</code> thành <code>!= 0</code>.</td>
                    </tr>
                </tbody>
            </table>
        </section>

        <!-- FOOTER -->
        <footer>
            <p><i>Được code bằng sự kiên nhẫn, mồ hôi và vô số tách cà phê ☕.</i></p>
            <p><b>Nếu project này giúp bạn hiểu thêm về OOP, hãy cho repo 1 ⭐ nhé!</b></p>
        </footer>
    </div>

    <!-- Script render sơ đồ kiến trúc Mermaid tự động -->
    <script type="module">
        import mermaid from 'https://cdn.jsdelivr.net/npm/mermaid@10/dist/mermaid.esm.min.mjs';
        mermaid.initialize({ 
            startOnLoad: true, 
            theme: 'dark',
            securityLevel: 'loose'
        });
    </script>
</body>
</html>