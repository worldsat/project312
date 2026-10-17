# Nexus Habitat Futuristic Smart Home Dashboard — Project Rules

You are an **Expert Senior Android Developer (Jetpack Compose), Flutter UI Engineer, and Mobile Glassmorphic UI Implementation Specialist**.

Build **Nexus Habitat** as a high-fidelity, maintainable, compile-ready **native mobile UI application** using:

- **For Android (Primary Target):**
  - Kotlin;
  - Jetpack Compose;
  - Material 3 (`androidx.compose.material3`);
  - single-Activity architecture;
  - MVVM and unidirectional data flow (UDF);
  - immutable screen UI state, `StateFlow`, and lifecycle-aware state collection (`collectAsStateWithLifecycle()`);
  - Compose Navigation for the four supported screens;
- **For Flutter (When Explicitly Targeted):**
  - Dart & Flutter Material 3;
  - strict separation between `StatelessWidget` presentation layers and state controllers (BLoC / Cubit / Riverpod Notifier) consuming a single immutable `ScreenState` and dispatching discrete `ScreenEvent`s;
  - `ClipRRect` + `BackdropFilter` for frosted glass surfaces and `CustomPainter` for gauges/splines;
- **Data Layer:**
  - locally seeded, deterministic demonstration telemetry and device state where no actual IoT backend or hardware hub exists.

**Primary objective:** Reproduce the supplied **Nexus Habitat** smart-home telemetry, room control, automation routine, and energy analytics UI with excellent visual fidelity, authentic **Obsidian Dark** and **Cyber Glassmorphism** styling, custom `Canvas` / `CustomPainter` graphics, and working, honest local UI interactions. This is **not** permission to invent production IoT networking (MQTT, Matter, Zigbee, WebSockets), Bluetooth/Wi-Fi hardware scanning, cloud authentication, live camera streaming, utility billing systems, or additional product screens.

This file **replaces the previous project's (Nova) app-specific rules**. Use the previous document only as an example of rule-file thoroughness; none of its authentication/profile features, sample content (`Elena Vance` / `Alex Morgan`), light indigo colors, assets, or screen flows belongs to **Nexus Habitat**.

> Platform boundary: By default, implement the target stack requested by the user (**Android / Jetpack Compose** or **Flutter / Dart**) using the cross-platform tokens defined in `design.md`. Do not mix both frameworks in a single task unless explicitly instructed.

---

## 1. Verified Project Inputs and Source of Truth

The materials actually supplied for this **Nexus Habitat** task are:

| Source | Supplied material | Intended use |
| --- | --- | --- |
| Root design specification | `design.md` (`DESIGN.md`) | Authoritative color palette, typography, spacing, shape tokens, glassmorphic surface rules, custom canvas specs, and architecture directives |
| Overview screenshot | `stitch_futuristic_glassmorphic_smart_home/smart_home_overview/screen.png` | Smart Home Overview visual layout, telemetry hierarchy, mode selector, and initial visual state |
| Rooms screenshot | `stitch_futuristic_glassmorphic_smart_home/rooms_zones/screen.png` | Rooms & Zones layout, filter tabs, room telemetry, sliders, and initial visual state |
| Routines screenshot | `stitch_futuristic_glassmorphic_smart_home/automations_routines/screen.png` | Automations & Routines layout, AI Synapse banner, IF/THEN card, and sequence toggles |
| Energy screenshot | `stitch_futuristic_glassmorphic_smart_home/energy_analytics/screen.png` | Energy Analytics layout, dual-arc Power Flux gauge, 24-hr curve, and appliance breakdown |
| Stitch HTML | Corresponding `code.html` in each of the four screen folders | Reference for labels, telemetry values, control intent, and demonstrated micro-interactions[cite: 2] |
| Embedded Stitch design | `stitch_futuristic_glassmorphic_smart_home/aetheria_glass/DESIGN.md`[cite: 2] | Supplementary visual background **only**; not authoritative over root `design.md` |

**Important evidence limitation:** No pre-existing Gradle/Flutter project, `src/` or `lib/` tree, ViewModels, IoT hardware SDK, standalone room WebP renders, standalone avatar PNG, or bundled **Space Grotesk** font file is included in the supplied archive. Do not claim that any of those has been reviewed or exists. If a repository is supplied later, inspect it before making implementation decisions.

Use this decision hierarchy:

1. **Screen screenshot (`screen.png`)** for visible composition, hierarchy, proportions, alignment, card density, initial look, and screen-specific chrome[cite: 2].
2. **Root `design.md`** for official color hex values, opacities, blur radii, typography scale, spacing, component shapes, M3 semantics, and implementation directives.
3. **Existing clean native source**, if actually supplied later, for project structure, package name, dependency versions, and already implemented behavior consistent with the current design.
4. **Matching Stitch `code.html`** to interpret control purpose, UI copy, telemetry labels, and demo interactions; translate to native behavior[cite: 2].
5. **Explicit user instructions** for later approved changes; document intentional deviations from the supplied reference.

When sources conflict:

- The screenshot wins on **screen layout** and initial visual hierarchy[cite: 2].
- The root `design.md` wins on **theming, exact tokens, and implementation conventions**.
- The root `design.md` wins over **both** the embedded `aetheria_glass/DESIGN.md` and each HTML file's generated Tailwind classes.
- HTML is an interaction and copy reference, **never** the actual mobile UI implementation[cite: 2].
- A missing asset, missing destination screen (`Settings`), or nonfunctional HTML action must not become an invented product requirement.

---

## 2. Project Identity and Supported Screen Scope

**Product name:** Nexus Habitat  
**Visual identity:** Futuristic Smart Home Dashboard  
**Visual style:** **Obsidian Dark Theme** blended with **Cyber Glassmorphism**, frosted translucent surfaces, 1dp rim-lighting borders, and glowing neon highlights (Electric Cyan, Violet Aurora, Emerald Eco).

Exactly **four complete screen references** are provided:

1. `SCREEN_OVERVIEW` (`/overview`) — `smart_home_overview/screen.png` — **Smart Home Overview**.
2. `SCREEN_ROOMS` (`/rooms`) — `rooms_zones/screen.png` — **Rooms & Zones**.
3. `SCREEN_ROUTINES` (`/routines`) — `automations_routines/screen.png` — **Automations & Routines**[cite: 1, 2].
4. `SCREEN_ENERGY` (`/energy`) — `energy_analytics/screen.png` — **Energy Analytics**[cite: 1, 2].

Implement those four screens[cite: 1, 2]. Do not add unsupported full screens such as:

- standalone `Settings` screen (even though `Settings` is the 5th bottom navigation item, no destination screen is supplied)[cite: 1, 2];
- Login, Sign Up, or User Profile editor;
- Notification center / inbox modal;
- Bluetooth / Wi-Fi / Matter device-pairing wizard for `Pair New Node`;
- Full node-graph visual editor screen for the `IF/THEN` sequence builder;
- Live CCTV camera stream viewer;
- Utility billing or solar tariff payment checkout.

Supported end-to-end demonstration navigation flow:

```text
[ NexusGlassBottomNavBar ]
   ├── /overview  (Smart Home Overview — Default Start Destination)
   ├── /rooms     (Rooms & Zones)
   ├── /routines  (Automations & Routines)
   ├── /energy    (Energy Analytics)
   └── Settings   (Triggers honest local demo feedback e.g. Snackbar; does NOT navigate to an invented screen)