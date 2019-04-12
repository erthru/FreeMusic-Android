<?php

$result = array();

array_push(

  $result,array(
    'title'=>'Summer',
    'artist'=>'Ashalumosic',
    'file'=>'http://192.168.56.1/anows/freemusic/music/music1.mp3',
    'art'=>'http://192.168.56.1/anows/freemusic/images/ash.jpg'
  )

  );

array_push(

  $result,array(
    'title'=>'Energy',
    'artist'=>'Bensound Music',
    'file'=>'http://192.168.56.1/anows/freemusic/music/music2.mp3',
    'art'=>'http://192.168.56.1/anows/freemusic/images/bensound.jpg'
  )

);

array_push(

  $result,array(
    'title'=>'Dance',
    'artist'=>'Ashalumosic',
    'file'=>'http://192.168.56.1/anows/freemusic/music/dance.mp3',
    'art'=>'http://192.168.56.1/anows/freemusic/images/dance.jpeg'
  )

);

array_push(

  $result,array(
    'title'=>'Summer Dance',
    'artist'=>'Ashalumosic',
    'file'=>'http://192.168.56.1/anows/freemusic/music/summerdance.mp3',
    'art'=>'http://192.168.56.1/anows/freemusic/images/summerdance.png'
  )

);

array_push(

  $result,array(
    'title'=>'Trap',
    'artist'=>'Ashalumosic',
    'file'=>'http://192.168.56.1/anows/freemusic/music/trap.mp3',
    'art'=>'http://192.168.56.1/anows/freemusic/images/trap.jpg'
  )

);



echo json_encode(array('music'=>$result));

?>
