# matchpoint-develop プロジェクト メモ

## プロジェクト概要

- **場所**: `C:\Program Files\matchpoint-develop\matchpoint-develop`
- **種別**: Spring Boot 2.7.0 + Thymeleaf + H2 Database
- **用途**: バドミントン大会・トーナメント管理システム

## 起動手順

PowerShell で以下を **1行ずつ** 実行する：

```powershell
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-25.0.2.10-hotspot"
$env:PATH = "C:\Windows\System32;" + $env:PATH
.\mvnw.cmd spring-boot:run
```

起動後、ブラウザで `http://localhost:8080` にアクセス。

## アクセス先

| URL | 内容 |
|---|---|
| `http://localhost:8080` | アプリ本体 |
| `http://localhost:8080/h2-console` | H2 DB 管理画面 |

H2 コンソールのログイン情報：
- JDBC URL: `jdbc:h2:mem:matchpoint`
- ユーザー名: `sa`
- パスワード: （空欄）

## DB 構成

- **DB**: H2 Database（組み込み型・インメモリ）
- **初期化ファイル**:
  - `src/main/resources/schema.sql` - テーブル・ビュー定義
  - `src/main/resources/data.sql` - 初期データ投入
- ※ アプリ再起動のたびにデータはリセットされる（インメモリのため）

## ポートが使用中エラーが出た場合

```powershell
Stop-Process -Id (Get-NetTCPConnection -LocalPort 8080).OwningProcess -Force
```

その後、再度 `.\mvnw.cmd spring-boot:run` を実行。

## 環境

- Java: `C:\Program Files\Eclipse Adoptium\jdk-25.0.2.10-hotspot` (Java 25)
- Maven Wrapper: プロジェクト内の `mvnw.cmd` を使用（Maven は PATH 不要）

## カスタム指示

### 「前回のセッションを振り返って」と言われた場合

`C:\Program Files\matchpoint-develop\` 配下のセッションファイルを確認する。
具体的には以下を探索する：
- `C:\Program Files\matchpoint-develop\` 直下のファイル・ディレクトリ一覧を確認
- セッションログ・作業記録に相当するファイルがあれば内容を読み込む
