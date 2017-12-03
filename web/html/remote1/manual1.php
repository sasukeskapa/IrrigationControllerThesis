<?php
  if(isset($_POST['json'])) {
    $json = $_POST['json'];
    //var_dump(json_decode($json, true));
    $outFile = fopen("../remote1_sources/manual_program.txt","w");
    fwrite($outFile, $json);
    fclose($outFile);
  } else if(isset($_POST['get'])) {
    $inFile = fopen("../remote1_sources/manual_program.txt","r");
    $content = fgets($inFile,1000);
    fclose($inFile);
    echo "$content";
  } else {
    echo "FAIL!";
  }
?>
