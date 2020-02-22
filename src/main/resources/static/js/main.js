var promote = async (repo, buildNumber, environment) => {
  const response = await fetch('http://localhost:8080/api/builds/promote', {
    method: 'POST',
    body: JSON.stringify({repo:repo, buildNumber:buildNumber, environment:environment}), // string or object
    headers: {
      'Content-Type': 'application/json'
    }
  });
  const myJson = await response.json();
  console.log("received response: ");
  console.log(myJson);
  location.reload();
}

function searchRepo() {
  var input, filter, row, rows, i, txtValue;
  input = document.getElementById('search');
  filter = input.value.toUpperCase();
  rows = document.getElementsByClassName('versionRow');

  for (i = 0; i < rows.length; i++) {
    row = rows[i].getElementsByTagName("h5")[0]
    txtValue = row.textContent || row.innerText;
    if (txtValue.toUpperCase().indexOf(filter) > -1) {
      rows[i].style.display = "";
    } else {
      rows[i].style.display = "none";
    }
  }
}
