/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 34.947839046199704, "KoPercent": 65.0521609538003};
    var dataset = [
        {
            "label" : "FAIL",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "PASS",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.16784649776453056, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [0.016853932584269662, 500, 1500, "HTTP Request-2"], "isController": false}, {"data": [0.0, 500, 1500, "HTTP Request-1"], "isController": false}, {"data": [0.5942895086321381, 500, 1500, "HTTP Request-0"], "isController": false}, {"data": [0.0, 500, 1500, "HTTP Request"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 2684, 1746, 65.0521609538003, 14533.726527570794, 141, 24989, 21001.0, 21819.5, 24683.5, 24907.15, 107.31278237575468, 309.47117643236976, 8.279325846627485], "isController": false}, "titles": ["Label", "#Samples", "FAIL", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions/s", "Received", "Sent"], "items": [{"data": ["HTTP Request-2", 178, 171, 96.06741573033707, 20241.17415730336, 584, 21031, 21002.0, 21004.1, 21012.05, 21023.1, 8.330213403219767, 68.13543470376264, 0.03743002094253089], "isController": false}, {"data": ["HTTP Request-1", 753, 575, 76.36122177954847, 16881.135458167326, 2932, 21043, 21001.0, 21004.0, 21011.0, 21027.0, 33.36730624363008, 86.64130699694243, 0.8704115799618912], "isController": false}, {"data": ["HTTP Request-0", 753, 0, 0.0, 789.4103585657373, 141, 15069, 750.0, 824.0, 849.3, 1064.92, 48.081220867122155, 8.357868471042718, 5.305837849594535], "isController": false}, {"data": ["HTTP Request", 1000, 1000, 100.0, 22099.67200000001, 20999, 24989, 21766.5, 24719.7, 24775.75, 24957.98, 39.98240774059414, 167.8516921304626, 4.139662923313742], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to www.avana.ru:443 [www.avana.ru/185.130.114.180] failed: Connection timed out: connect", 342, 19.587628865979383, 12.742175856929956], "isController": false}, {"data": ["Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to avana.ru:80 [avana.ru/185.130.114.180] failed: Connection timed out: connect", 247, 14.146620847651775, 9.202682563338302], "isController": false}, {"data": ["Test failed: text expected to contain /&copy; 1994&ndash;2023 АВАНА./", 7, 0.4009163802978236, 0.2608047690014903], "isController": false}, {"data": ["Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to avana.ru:443 [avana.ru/185.130.114.180] failed: Connection timed out: connect", 1150, 65.86483390607101, 42.846497764530554], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 2684, 1746, "Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to avana.ru:443 [avana.ru/185.130.114.180] failed: Connection timed out: connect", 1150, "Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to www.avana.ru:443 [www.avana.ru/185.130.114.180] failed: Connection timed out: connect", 342, "Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to avana.ru:80 [avana.ru/185.130.114.180] failed: Connection timed out: connect", 247, "Test failed: text expected to contain /&copy; 1994&ndash;2023 АВАНА./", 7, "", ""], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": ["HTTP Request-2", 178, 171, "Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to www.avana.ru:443 [www.avana.ru/185.130.114.180] failed: Connection timed out: connect", 171, "", "", "", "", "", "", "", ""], "isController": false}, {"data": ["HTTP Request-1", 753, 575, "Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to avana.ru:443 [avana.ru/185.130.114.180] failed: Connection timed out: connect", 575, "", "", "", "", "", "", "", ""], "isController": false}, {"data": [], "isController": false}, {"data": ["HTTP Request", 1000, 1000, "Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to avana.ru:443 [avana.ru/185.130.114.180] failed: Connection timed out: connect", 575, "Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to avana.ru:80 [avana.ru/185.130.114.180] failed: Connection timed out: connect", 247, "Non HTTP response code: org.apache.http.conn.HttpHostConnectException/Non HTTP response message: Connect to www.avana.ru:443 [www.avana.ru/185.130.114.180] failed: Connection timed out: connect", 171, "Test failed: text expected to contain /&copy; 1994&ndash;2023 АВАНА./", 7, "", ""], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
