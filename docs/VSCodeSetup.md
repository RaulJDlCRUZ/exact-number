# Visual Studio Code Setup

## Extensions

- GitHub Copilot (GitHub)
- GitHub Copilot Chat (GitHub)
- Extension Pack for Java (Microsoft)
- IntelliCode API Usage Examples
- Spring Boot Extension Pack (VMWare)

## Java Projects View (Eclipse-Like)

Enable "Hierarchical View" at Java Projects View

- Hide Non-Java Resources may help to focus on Java source files, and keep the classical VSCode Explorer to manage the entire project (including pom, docs...)
- Refresh acts like "Refresh Project" from Eclipse IDE for Java

## Maven Management

> Ensure the file `mvnw` (Linux executable) has execution permissions (`chmod +x`). Similar with `mvnw.cmd` in Windows

At Visual Studio Code:
1. View > Command Palette (Ctrl + Shift + P)
2. Type _Maven_
3. Select the command. Typically, is useful "Execute commands", because it contains all basic commands to manage a Maven Project (`clean`, `compile`, `test`...) or allows to create a custom one (for example: `clean install`).