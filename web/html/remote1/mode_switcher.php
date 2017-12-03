<?php
  $filename = "../remote1_sources/mode.txt";
  // set the mode if new value received
  if(isset($_POST['mode'])) {
    $mode = $_POST['mode'];
    $outFile = fopen($filename,"w");
    fwrite($outFile, $mode);
    fclose($outFile);
  }
  // return the stop state
  if (file_exists($filename)) {
    // return the state
    $inFile = fopen($filename,"r");
    $mode = fgets($inFile,1000);
    fclose($inFile);
    if ($mode == "0" || $mode == "1")
      echo $mode;
    else
      echo "0";
  } else {
    $outFile = fopen($filename,"w");
    fwrite($outFile, "0");
    fclose($outFile);
    echo "0";
}
?>
