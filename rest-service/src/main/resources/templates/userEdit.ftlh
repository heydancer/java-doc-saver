<#import "parts/common.ftlh" as c>

<@c.page>
    <form action="/users" method="post">
        <input type="text" name="username" value="${user.username}" class="form-control" placeholder="New name"
               style="margin-bottom: 10px;  width: 200px">
        <#list roles as role>
            <div>
                <label><input type="checkbox"
                              name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
            </div>
        </#list>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button class="btn btn-dark" type="submit" style="margin-top: 10px;">Сохранить</button>
    </form>
</@c.page>