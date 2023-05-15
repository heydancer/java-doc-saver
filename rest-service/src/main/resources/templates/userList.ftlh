<#import "parts/common.ftlh" as c>

<@c.page>
    <table class="table caption-top">
        <caption style="text-align:center">Список пользователей</caption>
        <thead class="table-dark">
        <tr>
            <th>Имя</th>
            <th>Тип доступа</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.username}</td>
                <td><#list user.roles as role>${role}<#sep>, </#list></td>
                <td>
                    <button class="btn btn-dark" type="submit">
                        <a href="/users/${user.id}" style="color: white; text-decoration:none">редактировать</a>
                    </button>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>
</@c.page>