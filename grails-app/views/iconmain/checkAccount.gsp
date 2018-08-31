<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Welcome to Grails</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico"/>
</head>

<body>
<content tag="nav">
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
           aria-expanded="false">ICON Operations <span class="caret"></span></a>
        <ul class="dropdown-menu">
            <li>
                <a href='<g:createLink controller="iconmain" action="checkAccountPage"
                                       params="${[address: address, tokenType: 'MNT']}"/>'>My Wallet (MNT)</a>
            </li>
            <li>
                <a href='<g:createLink controller="iconmain" action="checkAccountPage"
                                       params="${[address: address, tokenType: 'IDOL']}"/>'>My Wallet (IDOL)</a>
            </li>
            <li>
                <a href='<g:createLink controller="iconmain" action="transferToken"
                                       params="${[address: address, tokenType: 'MNT']}"/>'>Transfer Sample Token (MNT)</a>
            </li>
            <li>
                <a href='<g:createLink controller="iconmain" action="transferIdolToken"
                                       params="${[address: address, tokenType: 'IDOL']}"/>'>Transfer Idol Token (IDOL)</a>
            </li>
            <li role="separator" class="divider"></li>
        </ul>
    </li>
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
           aria-expanded="false">About<span class="caret"></span></a>
        <ul class="dropdown-menu">
            <li><a href="#1">ICON Foundation</a></li>
            <li><a href="#">ICON Token (ICX)</a></li>
            <li><a href="#">Sample Token</a></li>
        </ul>
    </li>
</content>

<div class="svg" role="presentation">
    <div class="grails-logo-container">
        <asset:image src="icon_logo.svg" class="grails-logo"/>
    </div>
</div>

<div id="content" role="main">
    <section class="row colset-2-its">
        <g:form controller="iconmain" action="checkAccountPage">
            <b>Owner's Address:</b> ${address}<br/>
            <b>SCORE Address (Current):</b> ${scoreAddress}<br/>
            <b>Token Symbol (Current):</b> ${tokenType}<br/>
            <label>Check Account:</label>
            <g:textField name="address" style="width: 500px"/><br/>
            <label>Token Type:</label>
            <g:select name="tokenType" from="${scoreMap.keySet()}"/><br/>
            <g:actionSubmit value="Check Account"/>
        </g:form>

    </section>
</div>

</body>
</html>
