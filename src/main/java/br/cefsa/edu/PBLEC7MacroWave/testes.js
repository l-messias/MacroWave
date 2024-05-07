(async function createOnda() {
          let photo = {"amplitude": 500, "frequencia": 5000, "fase": -90, "categoria": "triangular"};

          await fetch("http://localhost:8080/wave", {
                    method: "POST",
                    headers: {
                              "Accept": "application/json",
                              "Content-Type": "application/json"
                    },
                    body: JSON.stringify(photo)
                    })
                    .then(result => result.text())
                    .then(text => alert(text));
})();