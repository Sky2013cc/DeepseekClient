# DeepseekClient 🚀

<div align="center">
  <h1>☁️ DeepseekClient</h1>
  <p><strong>Minecraft 1.20.1 高级实用客户端</strong></p>
  <p>基于 Fabric — 50+ 功能 — 高端 ClickGUI</p>

  [![Minecraft](https://img.shields.io/badge/Minecraft-1.20.1-success)]()
  [![Fabric](https://img.shields.io/badge/Fabric-0.15.6-blue)]()
  [![License](https://img.shields.io/badge/License-MIT-purple)]()
</div>

---

## 📦 内置配置

客户端内置 5 套官方配置，针对不同场景一键加载：

| 配置 | 指令 | 适用场景 |
|------|------|---------|
| 🔴 **BedWars** | `.config load official_bedwars` | 起床战争（Grim 优化） |
| 🔵 **SkyWars** | `.config load official_skywars` | 空岛战争（Grim 优化） |
| 🛡️ **Grim通用** | `.config load official_grim` | 通用 Grim 反作弊 |
| 🌿 **原版** | `.config load official_vanilla` | 纯净/Paper 服务器 |
| ⚙️ **默认** | 自动加载 | 基础配置 |

---

## ✨ 功能列表

### 🗡️ 战斗 Combat
| 模块 | 说明 | Grim 绕过 |
|------|------|:--------:|
| KillAura | 自动攻击 | ✅ GCD旋转+随机延迟 |
| AimAssist | 平滑瞄准 | ✅ |
| AutoClicker | 自动连点 (可调CPS) | ✅ |
| AntiBot | 忽略机器人/NPC | ✅ |
| AntiKnockback | 防击退 (40%) | ✅ 非零检测绕过 |
| HitBox | 扩大碰撞箱 | ❌ Grim会检测 |
| MoreKB | 增加击退 | ✅ |
| AutoSoup | 自动喝汤 | ✅ |
| BowAimBot | 弓箭自动瞄准 | ✅ |
| AutoRod | 自动钓鱼竿 | ✅ |
| BedAura | 自动拆床 | ✅ |

### 🏃 移动 Movement
| 模块 | 说明 | Grim 绕过 |
|------|------|:--------:|
| Speed | 加速 (Strafe模式) | ✅ 跳跃加速 |
| Fly | 飞行 (Collision模式) | ✅ 碰撞箱飞行 |
| Sprint | 自动疾跑 | ✅ |
| NoSlowdown | 无减速 (吃东西/拉弓) | ✅ Mixin注入 |
| NoFall | 无摔落伤害 | ✅ 数据包欺骗 |
| KeepSprint | 攻击保持疾跑 | ✅ |
| TargetStrafe | 环绕目标 | ✅ |
| AntiWeb | 蜘蛛网防减速 | ✅ |
| Eagle | 边缘自动潜行 | ✅ 搭路安全 |
| AirStuck | 空中停滞 | ✅ |

### 👁️ 视觉 Visual
| 模块 | 说明 |
|------|------|
| ESP | 实体透视方框 |
| NameTags | 增强名称标签 |
| FullBright | 夜视 (Gamma) |
| XRay | 透视矿物 |
| BlockOverlay | 方块轮廓高亮 |
| ItemPhysical | 物品物理渲染 |
| StorageESP | 容器高亮 (箱子/末影箱) |
| Tracers | 实体追踪线 |
| Chams | 透视实体 |
| CameraNoClip | 摄像机穿墙 |

### 🧑 玩家 Player
| 模块 | 说明 |
|------|------|
| Scaffold | 自动搭路 (支持塔) |
| NoJumpDelay | 无跳跃延迟 |
| FreeLook | 自由视角 |
| AutoTools | 自动切换最佳工具 |
| FastPlace | 快速放置方块 |
| AutoPlace | 自动放置 |
| AutoFish | 自动钓鱼 |
| ChestSteal | 一键偷箱 |
| Refill | 自动补充快捷栏 |
| InvManager | 背包整理 |
| WaterBucket | 自动水桶 |
| MLG | 落地水 (自动) |
| HitSense | 打击感应 |
| Auto3rdPerson | 自动第三人称 |

### 🌍 世界 World
| 模块 | 说明 |
|------|------|
| Timer | 变速齿轮 |
| AntiFireball | 防恶魂火球 |
| BlockAnimation | 方块动画修改 |
| Projectiles | 投射物轨迹预测 |
| FPSBooster | FPS优化 |
| TimeChanger | 自定义时间 |
| Privacy | 隐私保护 (假名字) |
| Disabler | 反作弊绕过 |
| Blink | 瞬移回溯 |
| FakeLag | 假延迟 |
| KBBalance | 击退平衡 |

### ⚙️ 杂项 Misc
| 模块 | 说明 |
|------|------|
| HUD | 屏幕信息显示 |
| ClickGUI | 图形界面 (RSHIFT) |
| Commands | 指令系统 |
| Rotations | 旋转角度 |
| IRC | 聊天通信 (预留) |
| Friend | 好友系统 |
| HealthBypass | 血量绕过 |
| Spotify | Spotify集成 (预留) |
| LagDetector | 卡顿检测 |

---

## 🎮 操作

| 按键 | 功能 |
|------|------|
| `RSHIFT` | 打开 ClickGUI |
| `.toggle <模块>` | 开关模块 |
| `.bind <模块> <按键>` | 绑定按键 |
| `.modules` | 列出所有模块 |
| `.config official` | 加载 Grim 配置 |
| `.config load <名称>` | 加载指定配置 |
| `.config list` | 列出所有配置 |
| `.friend add/remove/list <名字>` | 好友管理 |
| `.help` | 显示帮助 |

---

## 🛠️ 安装

1. 安装 [Fabric Loader](https://fabricmc.net/use/)（Minecraft 1.20.1）
2. 下载最新的 DeepseekClient `.jar` 从 [Releases](https://github.com/Sky2013cc/DeepseekClient/releases)
3. 放入 `mods` 文件夹
4. 启动 Minecraft，选择 Fabric 版本

## 🔧 构建

```bash
git clone https://github.com/Sky2013cc/DeepseekClient.git
cd DeepseekClient
./gradlew build
# 输出: build/libs/DeepseekClient-*.jar
```

---

## 📄 许可证

MIT License © 2025 Sky_2013cc
