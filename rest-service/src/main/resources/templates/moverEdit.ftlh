<#import "parts/common.ftlh" as c>

<@c.page>
    <form action="/movers" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Имя:</label>
            <div class="col-sm-6">
                <input type="text" name="firstName" class="form-control" value="${mover.firstName}"
                       placeholder="first name"/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Фамилия:</label>
            <div class="col-sm-6">
                <input type="text" name="lastName" class="form-control" value="${mover.lastName}"
                       placeholder="last name"/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Email:</label>
            <div class="col-sm-6">
                <input type="text" name="email" class="form-control" value="${mover.email}"
                       placeholder="email"/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Подразделение:</label>
            <div class="col-sm-6">
                <input type="text" name="subdivision" class="form-control" value="${mover.subdivision}"
                       placeholder="subdivision"/>
            </div>
        </div>

        <div class="form-group row">
            <label class="col-sm-2 col-form-label">Звено:</label>
            <div class="col-sm-6">
                <input type="text" name="link" class="form-control" value="${mover.link}"
                       placeholder="link"/>
            </div>
        </div>
        <input type="hidden" value="${mover.id}" name="moverId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <button class="btn btn-dark" type="submit" style="margin-top: 10px;">Сохранить</button>
    </form>
</@c.page>