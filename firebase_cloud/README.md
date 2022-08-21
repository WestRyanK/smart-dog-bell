# How to Use

To build the docker image for firebase_cli do the following:
```powershell
docker build -f firebase_cli.dockerfile -t firebase_cli .
```

To use firebase in the docker container:
```powershell
docker run -it -p 4000:4000 -p 5000:5000 -p 5001:5001 -p 8080:8080 -p 8085:8085 -p 9000:9000 -p 9005:9005 -p 9099:9099 -p 9199:9199 -v "${PWD}:/firebase_project" firebase_cli bash
```

Then in the container:
```bash
firebase login
```

To host the Firebase Functions for local testing:
```bash
firebase serve --host 0.0.0.0
```

To invoke a Firebase Function:
```powershell
Invoke-WebRequest http://localhost:5000/smart-dog-bell/us-central1/myFunctionName
```