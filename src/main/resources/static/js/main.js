var promote = async (repo, buildNumber, environment) => {
  const response = await fetch('http://localhost:8080/api/builds/promote', {
    method: 'POST',
    body: JSON.stringify({repo:repo, buildNumber:buildNumber, environment:environment}), // string or object
    headers: {
      'Content-Type': 'application/json'
    }
  });
  const myJson = await response.json(); //extract JSON from the http response
  console.log("received response: ");
  console.log(myJson);
  location.reload();
}
