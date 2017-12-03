<?php
  $filename = "sources/lock.txt";
  // set the lock state if new value received
  if(isset($_POST['lock'])) {
    $lock = $_POST['lock'];
    $outFile = fopen($filename,"w");
    fwrite($outFile, $lock);
    fclose($outFile);
  }
  // return the lock state
  if (file_exists($filename)) {
    // return the state
    $inFile = fopen($filename,"r");
    $lock = fgets($inFile,1000);
    fclose($inFile);
    if ($lock == "0" || $lock == "1")
      echo $lock;
    else
      echo "0";
  } else {
    $outFile = fopen($filename,"w");
    fwrite($outFile, "0");
    fclose($outFile);
    echo "0";
}
?>
