<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <h5>Скачать все документы:</h5>
    <form method="get" action="/file/download/all">
        <div class="row">
            <div class="col-md-2">
                <input class="form-control" type="date" id="myDateTime" style="text-align: center"
                       name="rangeStart"
                       min="1970-01-01" max="9999-12-31">
            </div>
            <div class="col-md-2">
                <input class="form-control" type="date" id="myDateTime" style="text-align: center"
                       name="rangeEnd"
                       min="1970-01-01" max="9999-12-31"/>
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn btn-dark">Скачать</button>
            </div>
        </div>
    </form>

    <h5 style="margin-top: 20px">Поиск:</h5>
    <form method="get" action="/file">
        <div class="row">
            <div class="col-md-2">
                <label>
                    <input type="text" name="authorLastName" value="${authorLastName}" class="form-control" placeholder="Фамилия автора"/>
                </label>
            </div>
            <div class="col-md-2">
                <label>
                    <input type="text" name="subdivision" value="${subdivision}" class="form-control" placeholder="Подразделение"/>
                </label>
            </div>
            <div class="col-md-2">
                <label>
                    <input type="text" name="link" value="${link}" class="form-control" placeholder="Звено"/>
                </label>
            </div>
            <div class="col-md-2">
                <input class="form-control" type="date" id="myDateTime" style="text-align: center"
                       name="rangeStart"
                       min="1970-01-01" max="9999-12-31">
            </div>
            <div class="col-md-2">
                <input class="form-control" type="date" id="myDateTime" style="text-align: center"
                       name="rangeEnd"
                       min="1970-01-01" max="9999-12-31"/>
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn btn-dark">Найти</button>
            </div>
        </div>
    </form>

    <table class="table caption-top">
        <caption style="text-align:center">Список документов</caption>
        <thead class="table-dark">
        <tr>
            <th>№</th>
            <th>Имя</th>
            <th>Фамилия</th>
            <th>Подразделение</th>
            <th>№ Звена</th>
            <th>Дата загрузки документа</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list documents as doc>
            <tr>
                <td>${doc.id}</td>
                <td>${doc.authorFirstName}</td>
                <td>${doc.authorLastName}</td>
                <td>${doc.subdivision}</td>
                <td>${doc.link}</td>
                <td>${doc.created}</td>
                <td>
                    <form method="get" action="/file/download">
                        <input type="hidden" name="id" value="${doc.id}"/>
                        <button class="btn btn-dark" type="submit">Скачать</button>
                    </form>
                </td>
            </tr>
        <#else>
            Документы не найдены
        </#list>
        </tbody>
    </table>
</@c.page>