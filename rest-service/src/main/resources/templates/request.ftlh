<#import "parts/common.ftlh" as c>

<@c.page>
    <table class="table caption-top">
        <caption style="text-align:center">Список заявок</caption>
        <thead class="table-dark">
        <tr>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Email</th>
            <th>Подразделение</th>
            <th>№ Звена</th>
            <th>Статус</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list movers as mover>
            <tr>
                <td>${mover.getFirstName()}</td>
                <td>${mover.getLastName()}</td>
                <td>${mover.getEmail()}</td>
                <td>${mover.getSubdivision()}</td>
                <td>${mover.getLink()}</td>
                <td>${mover.getState()}</td>
                <td>
                    <form method="post" action="/movers/${mover.id}/request">
                        <input type="hidden" name="status" value="true">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <button class="btn btn-dark" type="submit">Принять</button>
                    </form>
                </td>
                <td>
                    <form method="post" action="/movers/${mover.id}/request">
                        <input type="hidden" name="moverId" value=${mover.id}>
                        <input type="hidden" name="status" value="false">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        <button class="btn btn-dark" type="submit">Отклонить</button>
                    </form>
                </td>
            </tr>
        </#list>
        </tbody>
    </table>

</@c.page>
