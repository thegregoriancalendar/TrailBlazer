# TrailBlazer Pathfollowing Library

![trailblazer logo (1)](https://github.com/user-attachments/assets/cc301840-7200-4694-9271-1e638a79e78b)

TrailBlazer is FTC Team 3888's pathfollowing library designed to provide smooth and efficient robot movement using Guided Vector Fields (GVF) and Bézier curves. The library enables robots to follow complex paths with precision, accounting for real-world imperfections and voltage variations.

## Features

- ✅ Smooth path generation with Bézier curves  
- 🎯 Accurate pathfollowing using Guided Vector Fields  
- ⚙️ Easy integration with FTC SDK  
- 🔋 Real-time voltage and loop frequency compensation

---

## 🚀 Getting Started

### 📥 Installation
1. Clone or download the **TrailBlazer** repository to your FTC project directory.
2. Add the `trailBlazer` package to your **TeamCode** module.

### 🗂️ Project Structure
```
TeamCode/
├─ trailBlazer/
│  ├─ gvf/        # GVF calculation classes
│  ├─ util/       # Utility classes (Pose2D, Vector2D, etc.)
│  └─ Examples/   # Example opmodes (see usage examples here)
```
➡️ **Example usage** can be found in [`TeamCode/trailBlazer/Examples`](TeamCode/trailBlazer/Examples).

---

## 🧩 Core Classes

### `PathGVF`  
Handles path generation and GVF-based power calculations.

```java
// Constructor
PathGVF(Vector2D[] waypoints);
```
- **waypoints:** Array of `Vector2D` points defining the path.

**Key Method:**
```java
Pose2D getPowers(Vector2D position, double voltage, double frequency);
```
Returns target motor powers based on the robot’s current position, battery voltage, and control loop frequency.

---

### `Vector2D`  
Represents a 2D point or vector.

```java
// Constructor
Vector2D(double x, double y);

// Methods
double getX();  // X coordinate  
double getY();  // Y coordinate
```

---

### `Pose2D`  
Describes the robot's position and orientation.

```java
// Constructor
Pose2D(Vector2D position, double heading);

// Methods
Vector2D getX();      // Translational X component  
Vector2D getY();      // Translational Y component  
Angle getAngle();    // Rotational component  
```

---

## 💡 Best Practices

✅ **Localization:** Replace placeholder `position` with your actual localization data.  
🔋 **Voltage Compensation:** Pass the current battery voltage to `getPowers` for consistent results.  
📈 **Loop Frequency:** Use accurate `hz` values for smooth control.  

---

## 📝 License

This project is licensed under the [MIT License](LICENSE). Feel free to use and modify it for your team’s needs.

---

## 🤝 Support & Contributions

- 💬 Open issues or feature requests on the [GitHub repository](#).
- 🛠️ Interested in contributing? Contact us at @obafgkmlty on discord or at greasedlightningrobotics@gmail.com

Happy Blazing Trails! 🚀
