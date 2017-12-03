<?php
  if(isset($_POST['json1'])) {
    $json = $_POST['json1'];
    //var_dump(json_decode($json, true));
    // opens countlog.txt to change new hit number
    $outFile = fopen("../remote1_sources/auto_program1.txt","w");
    fwrite($outFile, $json);
    fclose($outFile);
  } else if(isset($_POST['json2'])) {
    $json = $_POST['json2'];
    //var_dump(json_decode($json, true));
    // opens countlog.txt to change new hit number
    $outFile = fopen("../remote1_sources/auto_program2.txt","w");
    fwrite($outFile, $json);
    fclose($outFile);
  } else if(isset($_POST['json3'])) {
    $json = $_POST['json3'];
    //var_dump(json_decode($json, true));
    // opens countlog.txt to change new hit number
    $outFile = fopen("../remote1_sources/auto_program3.txt","w");
    fwrite($outFile, $json);
    fclose($outFile);
  } else if(isset($_POST['json4'])) {
    $json = $_POST['json4'];
    //var_dump(json_decode($json, true));
    // opens countlog.txt to change new hit number
    $outFile = fopen("../remote1_sources/auto_program4.txt","w");
    fwrite($outFile, $json);
    fclose($outFile);
  } else if(isset($_POST['json5'])) {
    $json = $_POST['json5'];
    //var_dump(json_decode($json, true));
    // opens countlog.txt to change new hit number
    $outFile = fopen("../remote1_sources/auto_program5.txt","w");
    fwrite($outFile, $json);
    fclose($outFile);
  } else if(isset($_POST['get1'])) {
    $inFile = fopen("../remote1_sources/auto_program1.txt","r");
    $content = fgets($inFile,1000);
    fclose($inFile);
    echo "$content";
  } else if(isset($_POST['get2'])) {
    $inFile = fopen("../remote1_sources/auto_program2.txt","r");
    $content = fgets($inFile,1000);
    fclose($inFile);
    echo "$content";
  } else if(isset($_POST['get3'])) {
    $inFile = fopen("../remote1_sources/auto_program3.txt","r");
    $content = fgets($inFile,1000);
    fclose($inFile);
    echo "$content";
  } else if(isset($_POST['get4'])) {
    $inFile = fopen("../remote1_sources/auto_program4.txt","r");
    $content = fgets($inFile,1000);
    fclose($inFile);
    echo "$content";
  } else if(isset($_POST['get5'])) {
    $inFile = fopen("../remote1_sources/auto_program5.txt","r");
    $content = fgets($inFile,1000);
    fclose($inFile);
    echo "$content";
  } else {
    echo "FAIL!";
  }
?>
