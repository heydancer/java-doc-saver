<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <div>
        <form method="post" action="/movers/distribution">
            <label>
                <input type="text" name="text" class="form-control" style="width: 800px" placeholder="Введите сообщение"/>
            </label>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn btn-dark" type="submit">Отправить</button>
        </form>
    </div>

    <form method="get" action="/movers">
        <label>
            <input type="text" name="lastName" class="form-control" value="${lastName}" placeholder="Введите фамилию">
        </label>
        <button class="btn btn-dark" type="submit">Найти</button>
    </form>

    <table class="table caption-top">
        <caption style="text-align:center">Список грузчиков</caption>
        <thead class="table-dark">
        <tr>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Email</th>
            <th>Подразделение</th>
            <th>№ Звена</th>
            <th>Изменить</th>
        </tr>
        </thead>
        <tbody>
        <#list movers as mover>
            <tr>
                <td>${mover.firstName}</td>
                <td>${mover.lastName}</td>
                <td>${mover.email}</td>
                <td>${mover.subdivision}</td>
                <td>${mover.link}</td>
                <td>
                    <button class="btn btn-dark" type="submit">
                        <a href="/movers/${mover.id}" style="color: white; text-decoration:none">редактировать</a>
                    </button>
                </td>
            </tr>
        <#else>
            Грузчики не найдены
        </#list>
        </tbody>
    </table>

</@c.page>
