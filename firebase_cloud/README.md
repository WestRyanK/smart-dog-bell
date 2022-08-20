# How to Use

To build the docker image for firebase_cli do the following:
```powershell
docker build -f firebase_cli.dockerfile -t firebase_cli .
```

To use firebase in the docker container:
```powershell
docker run -it -p 9005:9005 -v "${PWD}:/firebase_project" firebase_cli bash
```

Then in the container:
```bash
firebase login
```
