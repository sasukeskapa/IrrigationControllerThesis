<?php
  $filename = "sources/modified.txt";
  // set the modified state if new value received
  if(isset($_POST['modified'])) {
    $modified = $_POST['modified'];
    $outFile = fopen($filename,"w");
    fwrite($outFile, $modified);
    fclose($outFile);
  }
  // return the modified state
  if (file_exists($filename)) {
    // return the state
    $inFile = fopen($filename,"r");
    $modified = fgets($inFile,1000);
    fclose($inFile);
    if ($modified == "0" || $modified == "1")
      echo $modified;
    else
      echo "0";
  } else {
    $outFile = fopen($filename,"w");
    fwrite($outFile, "0");
    fclose($outFile);
    echo "0";
}
?>
