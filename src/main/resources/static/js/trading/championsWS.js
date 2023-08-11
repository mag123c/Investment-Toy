var sock = null;

function connectChampionsAll(){
    var ws = new SockJS("/championsAll");
    sock = ws;

    ws.onopen = function(){
        console.log("연결완료");
    }

    ws.onmessage = function(e){
    }

    ws.onclose = function(){
        console.log("종료");
    }
}

connectChampionsAll();