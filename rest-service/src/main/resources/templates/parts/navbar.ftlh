<#include "security.ftlh">
<#import "login.ftlh" as l>

<nav class="navbar navbar-expand-lg" style="background-color: #c447b2;">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">DocSaver</a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <#if isAdmin>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/movers">Бригада</a>
                    </li>
                </#if>
                <#if isAdmin>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/movers/request">Заявки</a>
                    </li>
                </#if>
                <#if isAdmin>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/file">Файлы</a>
                    </li>
                </#if>
                <#if isAdmin>
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/users">Пользователи</a>
                    </li>
                </#if>
            </ul>

            <ul class="navbar-nav ms-auto md-2 mb-pg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/login" style="font-size: 25px">&#127291;</a>
                </li>
            </ul>


            <div class="navbar-text me-3">${name}</div>

            <@l.logout />
        </div>
    </div>
</nav>