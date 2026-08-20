# EMI AE2 Fix

Forge 1.20.1 客户端模组，修复 EMI 在 Applied Energistics 2 合成确认/材料不足界面中的交互。

修复内容：

- `A`：收藏鼠标指向的缺失材料
- `R`：查询该材料的合成配方
- `U`：查询该材料的用途
- 保留 EMI 侧栏显示和普通界面行为

AE2 合成确认界面的材料表由 AE2 自绘，不使用原版 `Slot`。本模组通过 Mixin 将这些自绘材料转换为 EMI 可识别的堆栈，仅作用于 AE2 界面。

## 下载

直接下载最新构建：
[`emi-ae2-fix-1.0.6.jar`](https://github.com/Heartlesx/EMI-AE2-Fix/raw/main/build/libs/emi-ae2-fix-1.0.6.jar)

将 JAR 放入 Minecraft 实例的 `mods` 目录，并同时安装：

- Minecraft Forge 1.20.1
- EMI 1.1.22（Forge）
- Applied Energistics 2 15.4.10 或兼容版本

## 构建

```powershell
./gradlew.bat build
```

依赖的 EMI 和 AE2 开发 JAR 位于 `libs/`，不会提交到仓库。
