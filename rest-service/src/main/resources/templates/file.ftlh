<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>

<@c.page>
    <form method="get" action="/file/download">
            <div>${title}</div>
            <div class="row">
                <div class="col-md-2">
                    <input class="form-control" type="text" id="id" name="id" placeholder="Введите ID фотографии"/>
                </div>
                <div class="col-md-1">
                    <button type="submit" class="btn btn-dark">Скачать</button>
                </div>
            </div>
    </form>

    <form method="get" action="/file/download/all">
            <label style="margin-top: 10px" for="myDateTime">Выберите период:</label>
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
</@c.page>