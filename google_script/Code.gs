function doGet(request) {

var sheets = request.parameter.table;
  var sheet = SpreadsheetApp.getActive();
  var paints = sheet.getSheetByName(sheets);
  var data =[];
  var rlen= paints.getLastRow();
  var clen= paints.getLastColumn();
  var rows = paints.getDataRange();
  var test = rows.getValues();
  for(var i=1; i< test.length; i++){
    var datarow = test[i];
    var record = {};
    for(var j=1;j<clen;j++){
      if(datarow[j]!='')
      record[test[0][j]]= datarow[j];
    }
    data.push(record);
  }
  console.log(data);
  var result= JSON.stringify(data);
  return ContentService.createTextOutput(result).setMimeType(ContentService.MimeType.JSON);
}
