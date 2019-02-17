# File Boomerang

**File-Boomerang** is kotlin lib that shift a resource from web to S3

## Requirements

```
- jdk8
```

## Import sdk

### gradle
```
TBD - need to define if this package is handle in nexus, jcenter or other
```

### Example
```
FileBoomerang(FileBoomerangOptions(<bucket>, <log>)).resource(<url of a resource>).shipTo(<s3 path>)
```
