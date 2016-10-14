package lw.ssl.analyze.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

/**
 * Created on 14.10.2016;
 *
 * @author p.sinitskiy (adronex303@gmail.com);
 * @since 1.0.
 */
@Path("/report")
public class SingleReportController {

    @GET
    public Response getReport(@QueryParam("email") String email, @QueryParam("url") String url){
      //  PdfReportService.buildReport();
        return Response.status(200).entity(email).build();
    }
}
