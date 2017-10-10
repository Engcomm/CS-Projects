var x1 = 0;
var x2, op;
var startNew = false;

function getNumber(x) {
    if(document.getElementById("screen").value == "0" || startNew) {
        document.getElementById("screen").value = x;
        startNew = false;
    }
    else if (document.getElementById("screen").value == "-0") {
        document.getElementById("screen").value = '-' + x;
    }
    else
        document.getElementById("screen").value += x;
}

function getOperator(y) {
    if (x1 == 0) {
        x1 = Number(document.getElementById("screen").value);
        op = y;
        startNew = true;
    }
    else if (!startNew) {
        x2 = Number(document.getElementById("screen").value);
        result();
        op = y;
        startNew = true;      
    }
    else 
        op = y;
}

function result() {
    if (x1 != "Error") {
        if (op == '+')
            x1 += x2;
        else if (op == '-')
            x1 -= x2;
        else if (op == '*')
            x1 *= x2;
        else if (op == '÷') {
            if (x2 == 0)
                x1 = "Error";
            else
                x1 /= x2;
        }
    }
    document.getElementById("screen").value = x1;
    x2 = undefined;
}

function clearScreen() {
    document.getElementById("screen").value = "0";
    x1 = 0;
    x2 = undefined;
    op = undefined;
}

function posNeg() {
    if(document.getElementById("screen").value.charAt(0) == '-')  {
        document.getElementById("screen").value = document.getElementById("screen").value.substring(1, document.getElementById("screen").value.length);
        if (x2 == undefined)
            x1 = -x1;
    }
    else {
        var tmp = '-';
        document.getElementById("screen").value = tmp + document.getElementById("screen").value;
        if (x2 == undefined)
            x1 = -x1;
    }
}