<#macro login path isRegisterForm>
    <form action="${path}" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Имя пользователя:</label>
            <div class="col-sm-6">
                <input type="text" name="username" class="form-control" placeholder="User name"/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Пароль:</label>
            <div class="col-sm-6">
                <input type="password" name="password" class="form-control" placeholder="Password"/>
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <#if !isRegisterForm>
            <button class="btn btn-dark" type="submit" style="margin-left: 450px; margin-top: 10px">
                <a href="/registration" style="color: white; text-decoration:none">Регистрация</a>
            </button>
        </#if>
        <button class="btn btn-dark" type="submit" style="margin-top: 10px"><#if isRegisterForm>Create<#else>Вход</#if></button>

    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-dark" type="submit">Выход</button>
    </form>
</#macro>