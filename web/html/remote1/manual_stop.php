<?php
  $filename = "../remote1_sources/manual_stop_all.txt";
  // set the stop state if new value received
  if(isset($_POST['stop'])) {
    $stop = $_POST['stop'];
    $outFile = fopen($filename,"w");
    fwrite($outFile, $stop);
    fclose($outFile);
  }
  // return the stop state
  if (file_exists($filename)) {
    // return the state
    $inFile = fopen($filename,"r");
    $stop = fgets($inFile,1000);
    fclose($inFile);
    if ($stop == "0" || $stop == "1")
      echo $stop;
    else
      echo "0";
  } else {
    $outFile = fopen($filename,"w");
    fwrite($outFile, "0");
    fclose($outFile);
    echo "0";
}
?>
