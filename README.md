## 📋 TODO LIST 미션 개요

이번 미션은 TODO 리스트를 구현하는 과제입니다.  
Docker로 Spring 서버 및 MySQL 실행 후, Postman으로 API를 테스트해야 합니다.

---

## 🔑 로그인 기능

사용자는 이메일(email)과 비밀번호(password)를 통해 로그인해야 하며,  
로그인에 성공한 사용자만 TODO 리스트에 접근할 수 있습니다.

---

## 📌 TODO 리스트 기능

- TODO 등록 (최대 3개의 세부할 일 포함)
- TODO 상태 변경 (진행전, 진행중, 진행완료)
- TODO 및 세부 할 일 삭제 (개별/여러 개 일괄 삭제 가능)
- 예외 처리 및 테스트 코드 필수 포함

---

## ⚠️ 예외 처리

사용자의 모든 입력에 대해 예외 처리를 반드시 구현해야 하며,  
예외 발생 시 적절한 에러 메시지를 표시하고, 요청을 재처리해야 합니다.

예시:

```java
public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND.value(), "유저를 찾을 수 없습니다.");
    }
}


class UserManageUsecaseTest {

    private UserSaveService userSaveService;
    private UserValidateService userValidateService;
    private PasswordUtil passwordUtil;
    private UserManageUsecase userManageUsecase;

    @BeforeEach
    void setUp() {
        userSaveService = mock(UserSaveService.class);
        userValidateService = mock(UserValidateService.class);
        passwordUtil = mock(PasswordUtil.class);
        userManageUsecase = new UserManageUsecase(userSaveService, userValidateService, passwordUtil);
    }

    @Test
    @DisplayName("회원가입 시 비밀번호는 암호화되고 저장되어야 한다")
    void testRegisterUserSuccess() {
        // given
        String email = "test@example.com";
        String rawPassword = "myPassword123";
        String encryptedPassword = "encrypted123";
        String nickname = "Tester";

        UserRegisterDto dto = new UserRegisterDto(email, rawPassword, nickname);

        when(passwordUtil.encrypt(rawPassword)).thenReturn(encryptedPassword);

        // when
        userManageUsecase.register(dto);

        // then
        verify(userValidateService, times(1)).validateDuplication(email);
        verify(passwordUtil, times(1)).encrypt(rawPassword);
        verify(userSaveService, times(1)).save(any(User.class));
    }
}
