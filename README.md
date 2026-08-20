# EMI AE2 下单缺失收藏修复

这是一个 Forge 1.20.1 客户端模组，修复 EMI 在 Applied Energistics 2 下单数量/确认界面中消失的问题。

AE2 下单界面由 AE2 自己绘制配方表，因此菜单刻意不包含原版 `Slot`。EMI 1.1.22 会将这种无槽位界面误判为空界面，从而跳过渲染和输入处理。本项目的 Mixin 仅在 EMI 作出该判断时补充 AE2 界面范围，不影响普通界面的检测。

使用 Java 17 构建。将生成的 `build/libs/emi-ae2-fix-1.0.5.jar` 放入 Monifactory 的 `mods` 目录，并与 EMI 1.1.22 和 Applied Energistics 2 一起使用。

## 构建

```powershell
./gradlew.bat build
```

依赖的 EMI 和 AE2 开发 JAR 位于 `libs/`，不会提交到仓库。
