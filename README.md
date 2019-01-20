# Clean code guidelines

## General guidelines
```text
> resolve TODO's before push!
> no redundant code left in comments.
> line should be compact and under 120 columns. (for advanced users)
> leave the "world" a better place (all code is yours):
--> every change is "blessed", indentation, naming and refactoring.
--> the code you are "wondering" if to improve or not, will eventually serve others, and eventually serve you, treat the code as customer that will use it.
> no duplication of any kind.
> code should be read like a well written prose. [inspired by uncle bob]
```

### Functions
```text
> function length should ideally be about 4-5 lines. 10 lines is too long (including exception handling and logging).
> should do one thing only.
> function optimal num of params should be max of 2, in case the number is higher you should gather the params to object or there's a good chance the function do more it should.
> functions should be in reading order.
> function naming should be according to the specific lang
> private method should never! call public method.
```

```text
> function should have one level of indentation, for example (and it's a bad one):
```
```java
public class RestMoodClient implements MoodClient {

 @Override
 public MoodApi fetchMood(String identityId, UpdateMoodRequest updateMoodRequest) {
     return doFetchMood(createDetectMoodRequest(identityId, updateMoodRequest));
 }

 private MoodApi doFetchMood(DetectMoodRequest detectMoodRequest) {
     try {
         ResponseEntity<String> response = restOperations.exchange(domain + MOOD_PATH,
                 HttpMethod.PUT, createEntityFrom(detectMoodRequest), String.class);

         if (response.getStatusCode() != HttpStatus.OK) {
             throw new DelegationException(
                     "Fail to detect mood for image: " + detectMoodRequest.getImage(),
                     objectMapper.fromJson(response.getBody(), ErrorMsgApi.class),
                     response.getStatusCode().value());
         } else {
             return objectMapper.fromJson(response.getBody(), MoodApi.class);
         }
     } catch (RestClientException e) {
         throw new FailFetchMoodException("failed to fetch mood: " + e);
     }
 }
}
```

```text
> as the scope is bigger the function name should be shorter and vice versa, for example:
```
controller:

```java
   public class UserController {

    @RequestMapping(value = "/me/enablenotifications", method = {RequestMethod.PUT, RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value = "update user enableNotifications field", produces = "application/json")
    public void enableNotifications(@RequestBody SingleValue singleValue, @RequestHeader String identityId) {
    }
}
```
 repository:

 ```java
  public class UserRepositoryImpl implements UpdateUserRepository {

    public void updateEnableNotificationsFlag(String identityId, boolean notificationsState) {
        executeUpdateByIdentityId(identityId, Update.update(ENABLE_NOTIFICATIONS, notificationsState));
    }
   }
 ```

## Variables
```text
> as the scope is bigger the variable name is longer and vice versa.
```


## Testing guidelines
```text
> all production code should be 100% covered by tests (UT/IT/CT/E2E).
> test code should be clean and reused as production code.
> test must be STATELESS! & ISOLATED  and can run in any order.
> make it easy to add more tests.
> test should test (not assert) only one thing.
> should not add redundant tests.
> IMPORTANT! as the test code gets more specific -> production code should be more generic
> all tests (incase test creates resources) should handle cleanups!
```

```text
> test method name should be a story teller -> descriptive one liner that indicates on the feature :
```

```java
class ExpensifyParserTest {

 @Test
 fun parseExpensesList(){
 }

 @Test
 fun throwParseExceptionGivenEmptyRequiredData(){
 }
}
```
```text
> test should "speak" in its domain :
```
```java
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ExpensifyClientRequestErrorTest {

    private val app = AppDriver()
    private val expensifyType: String = SystemType.Expensify.name
    private val expensifyStub = ExpensifyStub
    private val papayaStub = PapayaServiceStub

    @BeforeAll
    fun init() {
        app.start()
        papayaStub.startServer(9898)
        expensifyStub.startServer(9998)
    }

    @Test
    fun shouldFailOnWrongIntegrationUrl() {
      app.trigger(2, expensifyType, metadataForWrongIntegrationUrl())
      BusyWaitUtil.busyWaitToFinish(papayaStub)
      assertTrue(papayaStub.errorMsg.contains("Connection refused"))
    }
}

```

## What should i look for as a code reviewer?
```text
> before code review:
  -> your code should be merged with dev/integration!
  -> verify all tests pass
> should meet all guidelines expectations.
> the requirements are fulfilled.
> required documentation is in place.
> swagger documentation if needed.
> should have required performance test (according to product user story).
> proper use of logging.
> in case monitoring required, make sure its implemented and easy to monitor on deployment.
> 3rd Parties usage (licenses, similar libs).
> update readme if needed.
> check if an existing library or module already implement the functionality that is reviewed.
> implementation inlined to a designed (incase we have one)
> exception handling, meaningful error messages
```

## Definition of done
```text
- the requirements are fulfilled.
- all production code is tested.
- the code has E2E tests that passes.
- api documentation (Swagger + Confluence).
- logging/monitoring.
- the feature is deployed on target environment.
```
