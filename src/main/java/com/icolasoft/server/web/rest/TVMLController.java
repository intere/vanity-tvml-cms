package com.icolasoft.server.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.icolasoft.server.domain.Tag;
import com.icolasoft.server.repository.TagRepository;
import com.icolasoft.server.repository.VideoRepository;
import com.icolasoft.server.web.rest.helpers.RootTVML;
import com.icolasoft.server.web.rest.helpers.TagTVML;
import com.icolasoft.server.web.rest.util.HeaderUtil;
import com.icolasoft.server.web.rest.util.PaginationUtil;
import com.icolasoft.server.domain.tvml.generated.Document;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

/**
 * REST controller for managing Tag.
 */
@RestController
@RequestMapping("/api")
public class TVMLController {

    private final Logger log = LoggerFactory.getLogger(TagResource.class);

    @Value("#{servletContext.contextPath}")
    private String servletContextPath;

    @Inject private TagRepository tagRepository;
    @Inject private VideoRepository videoRepository;

    /**
     * GET  /tvml : Get the root TVML document
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tags in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/tvml",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_XML_VALUE)
    @Timed
    public ResponseEntity<Document> getTVMLTags(HttpServletRequest request) throws URISyntaxException, MalformedURLException {
        log.debug("REST request to get the root /tvml XML document");

        String baseURL = getBaseUrl(request);

        Document doc = RootTVML.buildRootDocument(tagRepository, baseURL);

        return new ResponseEntity<>(doc, HttpStatus.OK);
    }

     /**
      * GET  /tags/:id : get the "id" tag.
      *
      * @param id the id of the tag to retrieve
      * @return the ResponseEntity with status 200 (OK) and with body the tag, or with status 404 (Not Found)
      */
     @RequestMapping(value = "/tvml/{id}",
         method = RequestMethod.GET,
         produces = MediaType.APPLICATION_XML_VALUE)
     @Timed
     public ResponseEntity<Document> getTVMLTag(@PathVariable String id) {
         log.debug("REST request to get Tag : {}", id);
         Tag tag = tagRepository.findOne(id);

         Document doc = TagTVML.getTagTVML(tag, videoRepository);

         return Optional.ofNullable(doc)
             .map(result -> new ResponseEntity<>(
                 result,
                 HttpStatus.OK))
             .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
     }

    protected String getBaseUrl(HttpServletRequest request) throws URISyntaxException, MalformedURLException {
        URL url = new URL(request.getRequestURL().toString());
        String host  = url.getHost();
        String userInfo = url.getUserInfo();
        String scheme = url.getProtocol();
        Integer port = url.getPort();

        URI uri = new URI(scheme, userInfo, host, port, servletContextPath, null, null);
        return uri.toString();
    }


    public static String getCurrentUrl(HttpServletRequest request) throws URISyntaxException, MalformedURLException {
        URL url = new URL(request.getRequestURL().toString());
        String host  = url.getHost();
        String userInfo = url.getUserInfo();
        String scheme = url.getProtocol();
        Integer port = url.getPort();
        String path = request.getAttribute("javax.servlet.forward.request_uri").toString();
        String query = request.getAttribute("javax.servlet.forward.query_string").toString();

        URI uri = new URI(scheme, userInfo, host, port, path, query, null);
        return uri.toString();
    }

}
