var sock = null;
var cRow = document.querySelectorAll('.c-row');

function connectChampionsAll(){
    let ws = new SockJS("/championsAll");
    sock = ws;

    ws.onopen = function(){
        console.log("연결완료");
    }

    ws.onmessage = function(e){
        let msg = e.data;
        let tmp = "://";

        if (msg.split(tmp)[0] == "priceChange") {
            priceChange(msg.split(tmp));
        }
    }

    ws.onclose = function(){
        console.log("종료");
    }
}

connectChampionsAll();
init();

function priceChange(msgSplit) {
    let championName = msgSplit[1];
    let price = msgSplit[2];
    let percent = msgSplit[3] + "%";
    let totalPrice = msgSplit[4];

    for(let i = 0; i < cRow.length; i ++) {
        if (cRow[i].firstElementChild.getAttribute('data-cid') == championName) {
            console.log("correct", championName);
            let beforePrice = cRow[1].children[1].textContent;

            cRow[i].children[0].querySelector('a > span').textContent = championName;
            cRow[i].children[1].textContent = price;
            cRow[i].children[2].textContent = percent;
            cRow[i].children[3].textContent = totalPrice;

            let borderColor;
            if (beforePrice > price) {
                borderColor = "red-border";
            } else if (beforePrice < price) {
                borderColor = "blue-border";
            }

            cRow[i].children[0].classList.add(borderColor);

            percentCheck(cRow[i]);

            setTimeout(() => {
                cRow[i].children[0].classList.remove(borderColor);
            })
            return;
        }
    }
}

function init() {
    for(let i = 0; i < cRow.length; i ++) {
        percentCheck(cRow[i]);
    }
}

function percentCheck(tableRow) {
    let percent = tableRow.children[2].textContent;
    if(percent == "0%") {
        tableRow.children[1].className = colorChange(1, "basic-percent");
        tableRow.children[2].className = colorChange(2, "basic-percent");
        tableRow.children[3].className = colorChange(3, "basic-percent");
    }
    else if(percent.includes("-")) {
        tableRow.children[1].className = colorChange(1, "minus-percent");
        tableRow.children[2].className = colorChange(2, "minus-percent");
        tableRow.children[3].className = colorChange(3, "minus-percent");
    }
    else {
        tableRow.children[1].className = colorChange(1, "plus-percent");
        tableRow.children[2].className = colorChange(2, "plus-percent");
        tableRow.children[3].className = colorChange(3, "plus-percent");
    }
}

function colorChange(num, text) {
    if(num == 1) return "c-price p-3 " + text;
    if(num == 2) return "c-percent p-3 " + text;
    if(num == 3) return "c-totalPrice p-3 " + text;
}