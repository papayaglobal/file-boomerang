# File Boomerang

**File-Boomerang** is kotlin lib that shift a resource from web to S3.

Note: aws profile that is configured on machine that invokes _File-Boomerang_, will be the profile it uses. 

## Requirements

```
- jdk8
```

## Import sdk

artifacts available @ [*bintray repositories*](https://bintray.com/papaya/file-boomerang/file-boomerang)

### gradle
```
dependencies {
  ...
    compile compile 'papaya:file-boomerang:<version>'
}
```

### maven
```
<dependency>
  <groupId>papaya</groupId>
  <artifactId>file-boomerang</artifactId>
  <version>put-version-here</version>
  <type>pom</type>
</dependency>
```

### Example
```
FileBoomerang(FileBoomerangOptions(<bucket>, <log>)).resource(<url of a resource>).shipTo(<s3 path>)
```

## License
MIT

-----
