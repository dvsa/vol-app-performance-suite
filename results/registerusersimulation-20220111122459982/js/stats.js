var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "40",
        "ok": "40",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "385",
        "ok": "385",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "6585",
        "ok": "6585",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "2778",
        "ok": "2778",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "1805",
        "ok": "1805",
        "ko": "-"
    },
    "percentiles1": {
        "total": "3578",
        "ok": "3578",
        "ko": "-"
    },
    "percentiles2": {
        "total": "3875",
        "ok": "3875",
        "ko": "-"
    },
    "percentiles3": {
        "total": "4820",
        "ok": "4820",
        "ko": "-"
    },
    "percentiles4": {
        "total": "6003",
        "ok": "6003",
        "ko": "-"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 14,
    "percentage": 35
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 26,
    "percentage": 65
},
    "group4": {
    "name": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "0.035",
        "ok": "0.035",
        "ko": "-"
    }
},
contents: {
"req_get-register-pa-f7fed": {
        type: "REQUEST",
        name: "get register page",
path: "get register page",
pathFormatted: "req_get-register-pa-f7fed",
stats: {
    "name": "get register page",
    "numberOfRequests": {
        "total": "20",
        "ok": "20",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "385",
        "ok": "385",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "4024",
        "ok": "4024",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "1400",
        "ok": "1400",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "1462",
        "ok": "1462",
        "ko": "-"
    },
    "percentiles1": {
        "total": "462",
        "ok": "462",
        "ko": "-"
    },
    "percentiles2": {
        "total": "3157",
        "ok": "3157",
        "ko": "-"
    },
    "percentiles3": {
        "total": "3882",
        "ok": "3882",
        "ko": "-"
    },
    "percentiles4": {
        "total": "3996",
        "ok": "3996",
        "ko": "-"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 14,
    "percentage": 70
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 6,
    "percentage": 30
},
    "group4": {
    "name": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "0.017",
        "ok": "0.017",
        "ko": "-"
    }
}
    },"req_register-a-new--4acad": {
        type: "REQUEST",
        name: "register a new account",
path: "register a new account",
pathFormatted: "req_register-a-new--4acad",
stats: {
    "name": "register a new account",
    "numberOfRequests": {
        "total": "20",
        "ok": "20",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "3506",
        "ok": "3506",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "6585",
        "ok": "6585",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "4157",
        "ok": "4157",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "763",
        "ok": "763",
        "ko": "-"
    },
    "percentiles1": {
        "total": "3759",
        "ok": "3759",
        "ko": "-"
    },
    "percentiles2": {
        "total": "4711",
        "ok": "4711",
        "ko": "-"
    },
    "percentiles3": {
        "total": "5167",
        "ok": "5167",
        "ko": "-"
    },
    "percentiles4": {
        "total": "6301",
        "ok": "6301",
        "ko": "-"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 0,
    "percentage": 0
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 20,
    "percentage": 100
},
    "group4": {
    "name": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "0.017",
        "ok": "0.017",
        "ko": "-"
    }
}
    }
}

}

function fillStats(stat){
    $("#numberOfRequests").append(stat.numberOfRequests.total);
    $("#numberOfRequestsOK").append(stat.numberOfRequests.ok);
    $("#numberOfRequestsKO").append(stat.numberOfRequests.ko);

    $("#minResponseTime").append(stat.minResponseTime.total);
    $("#minResponseTimeOK").append(stat.minResponseTime.ok);
    $("#minResponseTimeKO").append(stat.minResponseTime.ko);

    $("#maxResponseTime").append(stat.maxResponseTime.total);
    $("#maxResponseTimeOK").append(stat.maxResponseTime.ok);
    $("#maxResponseTimeKO").append(stat.maxResponseTime.ko);

    $("#meanResponseTime").append(stat.meanResponseTime.total);
    $("#meanResponseTimeOK").append(stat.meanResponseTime.ok);
    $("#meanResponseTimeKO").append(stat.meanResponseTime.ko);

    $("#standardDeviation").append(stat.standardDeviation.total);
    $("#standardDeviationOK").append(stat.standardDeviation.ok);
    $("#standardDeviationKO").append(stat.standardDeviation.ko);

    $("#percentiles1").append(stat.percentiles1.total);
    $("#percentiles1OK").append(stat.percentiles1.ok);
    $("#percentiles1KO").append(stat.percentiles1.ko);

    $("#percentiles2").append(stat.percentiles2.total);
    $("#percentiles2OK").append(stat.percentiles2.ok);
    $("#percentiles2KO").append(stat.percentiles2.ko);

    $("#percentiles3").append(stat.percentiles3.total);
    $("#percentiles3OK").append(stat.percentiles3.ok);
    $("#percentiles3KO").append(stat.percentiles3.ko);

    $("#percentiles4").append(stat.percentiles4.total);
    $("#percentiles4OK").append(stat.percentiles4.ok);
    $("#percentiles4KO").append(stat.percentiles4.ko);

    $("#meanNumberOfRequestsPerSecond").append(stat.meanNumberOfRequestsPerSecond.total);
    $("#meanNumberOfRequestsPerSecondOK").append(stat.meanNumberOfRequestsPerSecond.ok);
    $("#meanNumberOfRequestsPerSecondKO").append(stat.meanNumberOfRequestsPerSecond.ko);
}
