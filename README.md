<style>
  p: font: Times New Roman
</style>

<h1>VisualML 3D</h1>
</h3>An updated, refined version of <a href="https://github.com/hershyz/visualml">VisualML</a>.</h3>

<h3>Setting up your data:</h3>
<p>A data file must have three parameters in every line, seperated by a <code>, </code>.</p>
<p>The first parameter is an X value, the second is a Y value, and the third parameter is the category the respective point falls under.</p>
<p>For example:</p>
<code>1, 2, 4, category1</code>
<br>
<code>4, 1, 3, category2</code>
<br>
<br>

<h3>Importing and making predictions:</h3>
<p>Import class with a path to a data file:</p>
<code>visualml3d vml3d = new visualml3d("data.txt");</code><br>
<p>Load and analyze values:</p>
<code>vml3d.init();</code><br>
<p>Display diagnostic summary of analyzed values:</p>
<code>vml3d.printSummary();</code><br>
<p>Predict a category of a new point based on existing data:</p>
<code>String predictedCategory = vml3d.predictCategory(21, 20, 22);</code>
