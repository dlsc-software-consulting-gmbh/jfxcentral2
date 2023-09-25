## SVG Path Extraction Tool Tips

### 1. Application Scenario
Often, there's a need to modify the shape of nodes through CSS `-fx-shape: "M8.03015 11L2 5H14.0603L8.03015 11Z";` or programmatically via `setShape(svgPath)`. This tool facilitates the extraction of the path data from SVG files, making it easier for you to apply custom shapes to your nodes.

### 2. Preview Discrepancies
The top preview displays the SVG image while the bottom preview showcases the actual JavaFX rendering. Please rely on the bottom preview for a true representation of the final output in JavaFX.

### 3. Limitations
If your SVG file contains geometric shapes like Rectangles, Circles, or Polygons, they cannot be converted into a Path format using this tool.