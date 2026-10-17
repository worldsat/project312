# DESIGN.md — Futuristic Smart Home Dashboard ("Nexus Habitat")
## Cross-Platform UI Specification for Jetpack Compose (Kotlin) & Flutter (Dart) — Material 3 & Glassmorphism

---

## 1. GLOBAL DESIGN TOKENS (Material 3)

### 1.1 Color Palette & M3 Semantic Roles

The design aesthetic blends **Obsidian Dark Theme** with **Cyber Glassmorphism** and glowing neon highlights (Electric Cyan, Violet Aurora, Emerald Eco).

| Token / Semantic Role | Hex Value | Opacity / Filter | Usage & Material 3 Mapping |
| :--- | :--- | :--- | :--- |
| **Primary** | `#00F2FE` | 100% | Electric Cyan: Brand accents, active states, circular gauge progress, primary icons. |
| **OnPrimary** | `#081018` | 100% | High-contrast dark text/icon on top of Electric Cyan containers. |
| **PrimaryContainer** | `#003544` | 80% | Subtle glowing background for active pill chips and badges. |
| **OnPrimaryContainer** | `#66F6FF` | 100% | Vibrant cyan text on primary containers. |
| **Secondary (Neon Purple)** | `#8B5CF6` | 100% | Violet Aurora: Secondary toggles, night mode accents, water heater telemetry. |
| **OnSecondary** | `#FFFFFF` | 100% | Crisp white icons/text over secondary accents. |
| **SecondaryContainer** | `#2E1065` | 85% | Subtle background tint for night/security routines. |
| **Tertiary (Eco Emerald)** | `#10B981` | 100% | Solar inflow indicator, optimal AQI badge, energy savings tags. |
| **Background** | `#0A0E18` | 100% | Deep Obsidian Void base canvas. |
| **OnBackground** | `#F8FAFC` | 100% | High-emphasis title text (Slate 50). |
| **Surface** | `#0F131D` | 100% | Default app bar, bottom navigation background, and base container. |
| **OnSurface** | `#E2E8F0` | 100% | Body copy, primary labels, card headings (Slate 200). |
| **OnSurfaceVariant** | `#94A3B8` | 100% | Secondary captions, timestamps, inactive icons (Slate 400). |
| **SurfaceVariant (Glass Card)** | `#171B26` | 65% with `blur(20dp)` | Glassmorphic card body with backdrop blur. |
| **SurfaceContainerLowest** | `#070A11` | 100% | Recessed gauge track background. |
| **SurfaceContainerHigh** | `#1E2433` | 75% | Elevated cards, dialogs, bottom sheets. |
| **Outline (Glass Stroke)** | `#FFFFFF` | 12% (`0x1FFFFFFF`) | 1dp outer border giving the frosted glass rim effect. |
| **OutlineVariant** | `#334155` | 40% | Subtle dividers and inactive progress tracks. |
| **Error / Alert** | `#F43F5E` | 100% | Peak usage warnings, security alerts, disconnect states. |

---

### 1.2 Typography & Scale Mappings

*Primary Font Family:* **Space Grotesk** (Futuristic Sans), with fallback to **Roboto / Inter**.

| M3 Typography Role | Compose `sp` / Weight | Flutter `TextStyle` | Line Height | Tracking / Letter Spacing |
| :--- | :--- | :--- | :--- | :--- |
| **Display Large** | `32.sp` / `FontWeight.Bold` (700) | `fontSize: 32, w700` | `38.sp` / `height: 1.18` | `-0.5sp` / `-0.5` |
| **Headline Medium** | `24.sp` / `FontWeight.SemiBold` (600) | `fontSize: 24, w600` | `30.sp` / `height: 1.25` | `-0.2sp` / `-0.2` |
| **Headline Small** | `20.sp` / `FontWeight.SemiBold` (600) | `fontSize: 20, w600` | `26.sp` / `height: 1.30` | `0.sp` / `0.0` |
| **Title Medium** | `16.sp` / `FontWeight.Medium` (500) | `fontSize: 16, w500` | `22.sp` / `height: 1.37` | `0.15sp` / `0.15` |
| **Title Small** | `14.sp` / `FontWeight.SemiBold` (600) | `fontSize: 14, w600` | `18.sp` / `height: 1.28` | `0.1sp` / `0.1` |
| **Body Large** | `15.sp` / `FontWeight.Normal` (400) | `fontSize: 15, w400` | `22.sp` / `height: 1.46` | `0.25sp` / `0.25` |
| **Body Medium** | `13.sp` / `FontWeight.Normal` (400) | `fontSize: 13, w400` | `18.sp` / `height: 1.38` | `0.25sp` / `0.25` |
| **Label Large (Buttons)** | `14.sp` / `FontWeight.SemiBold` (600) | `fontSize: 14, w600` | `20.sp` / `height: 1.42` | `0.5sp` / `0.5` (All-Caps opt) |
| **Label Small (Telemetry)**| `11.sp` / `FontWeight.Medium` (500) | `fontSize: 11, w500` | `14.sp` / `height: 1.27` | `0.5sp` / `0.5` |

---

### 1.3 Spacing, Insets & Shape Tokens

| Token | Dimension (dp / px) | Description & Target Elements |
| :--- | :--- | :--- |
| `spacing-xxs` | `4.dp` | Inline icon-to-label separation, badge padding. |
| `spacing-xs` | `8.dp` | Grid gap between micro-stat chips, internal card item spacing. |
| `spacing-sm` | `12.dp` | Compact vertical margins, segmented tab gaps. |
| `spacing-md` | `16.dp` | Screen standard horizontal gutter / padding (`PaddingValues(horizontal = 16.dp)`). |
| `spacing-lg` | `20.dp` | Section separation padding, internal hero card insets. |
| `spacing-xl` | `24.dp` | Spacing between major vertical modular sections. |
| `spacing-xxl`| `32.dp` | Bottom list clearance above fixed navigation bar (`84.dp` total nav clearance). |
| **Radius Small** | `12.dp` | Metric mini-chips, segmented tab buttons. |
| **Radius Medium**| `20.dp` | Quick-action cards, secondary telemetry widgets. |
| **Radius Large** | `28.dp` | Hero power flux card, room status cards, routine builder cards. |
| **Radius Full**  | `999.dp` | Pill switches, system status badges, action FABs, interactive pills. |

---

## 2. SCREEN REFERENCE GALLERY

| Screen Title | ID / Route | Structural Purpose & Key Interactive Modules |
| :--- | :--- | :--- |
| **Smart Home Overview** | `/overview` | Primary dashboard providing holistic habitat telemetry (Ambient Temp 22.5°C, Humidity 48%, AQI 14), System Mode switches (Home, Away, Night Protocol), Instant Quick Actions 2x2 grid, Real-Time Energy Vector wave, and Active Device quick-sliders. |
| **Rooms & Zones** | `/rooms` | Zone-by-zone environmental control. Features filterable horizontal tabs (All Zones, Living Room, Cyber Kitchen, Holodeck Studio), detailed environment stats (Lux, AQI, dB), master room toggle, lighting hue sliders, and "Pair New Node" action. |
| **Automations & Routines** | `/routines` | Logic architect for autonomous smart-living sequences. Displays AI Protocol Synapse stats (99.8% nominal), IF/THEN sequence builder card, and active sequence cards (Sunrise Awakening, Focus Matrix, Sunset Transition, Cyber Security Lockout) with smooth neon state switches. |
| **Energy Analytics** | `/energy` | Live telemetry & predictive eco-routing. Features dual-arc Power Flux gauge (Demand 2.84 kW vs. Solar Inflow +1.65 kW & Grid Pull 1.19 kW), 24-hr consumption variance curve, active appliance drain breakdown with eco-tips, and autonomous load balancing executor. |

---

## 3. COMPONENT-LEVEL & LAYOUT SPECIFICATIONS

### 3.1 Global Chrome & App Scaffold
- **Compose:**
  ```kotlin
  Scaffold(
      topBar = { NexusHabitatTopAppBar(...) },
      bottomBar = { NexusGlassBottomNavBar(...) },
      containerColor = MaterialTheme.colorScheme.background
  ) { innerPadding ->
      LazyColumn(
          modifier = Modifier.fillMaxSize().padding(innerPadding),
          contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
          verticalArrangement = Arrangement.spacedBy(20.dp)
      ) { ... }
  }
  ```
- **Flutter:**
  ```dart
  Scaffold(
    backgroundColor: const Color(0xFF0A0E18),
    appBar: const NexusHabitatAppBar(),
    bottomNavigationBar: const NexusGlassBottomNavBar(),
    body: SafeArea(
      child: ListView(
        padding: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 12.0),
        children: const [ ... ],
      ),
    ),
  );
  ```

---

### 3.2 Top App Bar (Header)
- **Visual Spec:** Dark frosted glass container (`#0F131D`, 85% opacity, `blur: 16dp`). Height: `64.dp`.
- **Leading:** Custom hexagonal cyber-home brand emblem (`#00F2FE` icon inside `#003544` circular container).
- **Title Block:** `Column` (Compose) / `Column` (Flutter) with cross-axis alignment start:
    - Top line: "Nexus Habitat" (`TitleMedium`, bold `#F8FAFC`) + live pulsing green dot indicator (`#10B981`, 6dp).
    - Sub line: "Optimal • 24°C" (`LabelSmall`, cyan tint `#00F2FE`).
- **Trailing Actions:**
    - Notification Bell Icon (`Icons.Outlined.Notifications`) with unread neon cyan badge dot.
    - User Avatar: Circular image/emblem (`36.dp`) framed with a 1.5dp glass outline (`0x33FFFFFF`).

---

### 3.3 Glassmorphic Surface Container (Card Pattern)
- **Visual Spec:** Frosted obsidian card with dynamic linear gradient background, subtle outer glow, and delicate 1dp translucent border.
- **Compose Specification:**
  ```kotlin
  @Composable
  fun GlassContainer(
      modifier: Modifier = Modifier,
      cornerRadius: Dp = 24.dp,
      content: @Composable BoxScope.() -> Unit
  ) {
      Box(
          modifier = modifier
              .clip(RoundedCornerShape(cornerRadius))
              .background(
                  brush = Brush.verticalGradient(
                      colors = listOf(
                          Color(0xFF171B26).copy(alpha = 0.75f),
                          Color(0xFF0F131D).copy(alpha = 0.85f)
                      )
                  )
              )
              .border(
                  width = 1.dp,
                  brush = Brush.linearGradient(
                      colors = listOf(
                          Color.White.copy(alpha = 0.18f),
                          Color.White.copy(alpha = 0.04f)
                      )
                  ),
                  shape = RoundedCornerShape(cornerRadius)
              )
              .padding(16.dp),
          content = content
      )
  }
  ```
- **Flutter Specification:**
  ```dart
  Widget buildGlassContainer({
    required Widget child,
    double radius = 24.0,
    EdgeInsetsGeometry padding = const EdgeInsets.all(16.0),
  }) {
    return ClipRRect(
      borderRadius: BorderRadius.circular(radius),
      child: BackdropFilter(
        filter: ImageFilter.blur(sigmaX: 16, sigmaY: 16),
        child: Container(
          padding: padding,
          decoration: BoxDecoration(
            borderRadius: BorderRadius.circular(radius),
            gradient: LinearGradient(
              begin: Alignment.topCenter,
              end: Alignment.bottomCenter,
              colors: [
                const Color(0xFF171B26).withOpacity(0.75),
                const Color(0xFF0F131D).withOpacity(0.85),
              ],
            ),
            border: Border.all(
              color: Colors.white.withOpacity(0.14),
              width: 1.0,
            ),
          ),
          child: child,
        ),
      ),
    );
  }
  ```

---

### 3.4 Interactive Neon Pill Switch & Mode Toggle
- **Visual Spec:** Pill track (`width: 52.dp`, `height: 28.dp`, `RoundedCornerShape(999.dp)`).
    - Unchecked: Track `#1E2433`, Thumb `#64748B`.
    - Checked (Cyan): Track glowing `#00F2FE`, Thumb `#081018` with icon checkmark.
    - Checked (Purple): Track glowing `#8B5CF6`, Thumb `#0F131D`.
- **System Mode Selector:**
    - Horizontal container with 3 pill segments: `[ Home ]`, `[ Away ]`, `[ Night Protocol ]`.
    - Selected tab has linear gradient (`#00F2FE` to `#0099B8`), high-contrast dark text, and subtle glow box shadow.

---

### 3.5 Custom Gauges & Graph Canvas
- **Circular Power Flux Gauge:**
    - Dual concentric arc tracks:
        1. Base Track: `360°` circle, stroke `12.dp`, color `#0F172A`.
        2. Active Demand Arc: `260°` sweep, gradient from `#00F2FE` to `#8B5CF6`, round cap ends.
        3. Self-sustained Arc: Inner secondary ring (`8.dp` stroke) in `#10B981`.
    - Compose implementation: `Canvas(modifier = Modifier.size(220.dp)) { drawArc(...) }`.
    - Flutter implementation: `CustomPaint(size: Size(220, 220), painter: PowerFluxPainter())`.
- **Telemetry Sparkline & Consumption Waves:**
    - Smooth Bézier curve with quadratic/cubic interpolation (`drawPath` / `Path.quadraticBezierTo`).
    - Gradient under-fill: `#00F2FE` (top 25% alpha) fading to `Transparent` at graph baseline.

---

### 3.6 Bottom Navigation Bar
- **Visual Spec:** Fixed frosted glass bar (`height: 72.dp`), inset padding `horizontal: 16.dp, bottom: 12.dp`.
- **5 Navigation Items:**
    1. `Overview` (`Icons.Default.GridView` / `Icons.grid_view_rounded`)
    2. `Rooms` (`Icons.Default.MeetingRoom` / `Icons.meeting_room_rounded`)
    3. `Routines` (`Icons.Default.Autorenew` / `Icons.autorenew_rounded`)
    4. `Energy` (`Icons.Default.Bolt` / `Icons.bolt_rounded`)
    5. `Settings` (`Icons.Default.Settings` / `Icons.settings_rounded`)
- **Active State:** Highlighted in `#00F2FE` with glowing dot underneath or subtle cyan icon glow.

---

## 4. FIXED IMAGE & ICON ASSET REGISTRY

| Asset Identifier | Asset Category | Semantic Role / Description | Recommended Vector / Asset Source |
| :--- | :--- | :--- | :--- |
| `ic_nexus_logo` | Brand Icon | Hexagonal cyber-habitat smart logo mark with glowing cyan node. | Custom Vector XML / Flutter `CustomPainter` |
| `ic_user_avatar` | Image Asset | Cyberpunk styled user profile headshot (36x36dp, circular clip). | Asset PNG / Network Image Placeholder |
| `ic_temp_ambient` | System Icon | Thermometer gauge indicator (`#00F2FE`). | `Icons.Rounded.Thermostat` |
| `ic_humidity` | System Icon | Droplet moisture percentage indicator (`#38BDF8`). | `Icons.Rounded.WaterDrop` |
| `ic_aqi_clean` | System Icon | Air quality leaf / eco wind breeze indicator (`#10B981`). | `Icons.Rounded.Air` |
| `ic_solar_inflow`| Telemetry Icon | Solar panel energy generation arrow (`#10B981`). | `Icons.Rounded.SolarPower` |
| `ic_grid_pull` | Telemetry Icon | Power station / electric transmission icon (`#8B5CF6`). | `Icons.Rounded.ElectricBolt` |
| `ic_light_bulb` | Device Icon | Cyber light bulb with adjustable lumen slider (`#00F2FE`). | `Icons.Rounded.Lightbulb` |
| `ic_climate_pod` | Device Icon | AC fan / Snowflake cooling status indicator (`#38BDF8`). | `Icons.Rounded.AcUnit` |
| `ic_sound_shield`| Device Icon | Speaker with acoustic soundwave isolation bar (`#8B5CF6`). | `Icons.Rounded.Speaker` |
| `ic_smart_blinds`| Device Icon | Automated horizontal shutter blinds (`#10B981`). | `Icons.Rounded.Curtains` |
| `img_room_living`| Ambient Visual | Dark futuristic living room render with subtle neon cyan lighting. | Asset WebP (16:9, rounded 20dp) |
| `img_room_studio`| Ambient Visual | Holodeck workstation simulator with dual neon rigs. | Asset WebP (16:9, rounded 20dp) |

---

## 5. AI IMPLEMENTATION SPECIAL INSTRUCTIONS (Cursor / Copilot)

```markdown
### AGENT SYSTEM DIRECTIVE: Jetpack Compose & Flutter M3 Code Generation

#### 1. Architecture & State Management
- Compose (Kotlin):
  * Strictly follow Unidirectional Data Flow (UDF).
  * Expose immutable UI state from ViewModels using `StateFlow<ScreenUiState>`.
  * Collect state via `collectAsStateWithLifecycle()` in root screen composables.
  * Hoist state completely: screen composables MUST accept `(state: ScreenUiState, onAction: (ScreenAction) -> Unit)`.
  * Never instantiate or call ViewModels inside reusable presentation widgets.
- Flutter (Dart):
  * Maintain clean separation between Stateless presentation widgets and State controllers (BLoC / Cubit / Riverpod Notifier).
  * Every screen root must consume a single immutable `ScreenState` and dispatch discrete `ScreenEvent`s.

#### 2. Glassmorphism & UI Styling Rules
- Strict Color Usage:
  * Compose: Bind all colors to `MaterialTheme.colorScheme` extensions (`electricCyan`, `glassSurface`, etc.).
  * Flutter: Use `Theme.of(context).colorScheme` with custom `ThemeExtension<GlassThemeTokens>`.
- Frosted Blur Implementation:
  * In Flutter, wrap glass containers in `ClipRRect` -> `BackdropFilter(filter: ImageFilter.blur(sigmaX: 16, sigmaY: 16))` -> `Container(decoration: BoxDecoration(...))`.
  * In Jetpack Compose (Android 12+), apply `Modifier.graphicsLayer { renderEffect = BlurEffect(16f, 16f) }` or supply fallback translucent gradient fills (`0xBF171B26`) on API < 31.
- Border & Rim Lighting:
  * Always apply a 1dp linear gradient stroke (`White.copy(0.18f)` to `White.copy(0.04f)`) to create the physical glass bevel illusion.

#### 3. Custom Canvas & Graphics Execution
- Never simulate complex curves using static icons.
- Energy Gauges: Use `Canvas` / `CustomPainter` with `drawArc` using `Stroke(cap = StrokeCap.Round)`.
- Trend Lines: Draw continuous Bézier splines using `Path.cubicTo()` / `path.cubicTo()` with gradient brush strokes (`Brush.horizontalGradient(listOf(Color(0xFF00F2FE), Color(0xFF8B5CF6)))`).
- CTA Button:
  * Must be a full-width pill button (`RoundedCornerShape(999.dp)` / `StadiumBorder`).
  * Gradient fill: Linear gradient from `#00F2FE` (Cyan) to `#00A3FF` (Deep Electric Blue).
  * Content: Bold dark text (`#081018`) with uppercase tracking and leading icon.
```
