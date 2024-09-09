package com.rjay.user.service;

import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.rjay.user.entity.UserCalendar;
import com.rjay.user.repository.CalendarRepository;


@Service
public class GoogleCalendarService {

	private static final String APPLICATION_NAME = "Spring Boot Google Calendar Integration";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens";
	private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
	private static final java.util.List<String> SCOPES = Collections
			.singletonList("https://www.googleapis.com/auth/calendar");

	@Autowired
	private CalendarRepository calendarRepository;

	private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws Exception {
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
				new InputStreamReader(getClass().getResourceAsStream(CREDENTIALS_FILE_PATH)));

		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES)
				.setDataStoreFactory(new FileDataStoreFactory(Paths.get(TOKENS_DIRECTORY_PATH).toFile()))
				.setAccessType("offline").build();

		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}

	/*
	 * private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
	 * throws Exception { // Load client secrets from the CREDENTIALS_FILE_PATH
	 * GoogleClientSecrets clientSecrets = GoogleClientSecrets.load( JSON_FACTORY,
	 * new InputStreamReader(getClass().getResourceAsStream(CREDENTIALS_FILE_PATH))
	 * );
	 * 
	 * // Build the Google Authorization Code Flow, which includes offline access
	 * and required scopes GoogleAuthorizationCodeFlow flow = new
	 * GoogleAuthorizationCodeFlow.Builder( HTTP_TRANSPORT, JSON_FACTORY,
	 * clientSecrets, SCOPES) .setDataStoreFactory(new FileDataStoreFactory(new
	 * File(TOKENS_DIRECTORY_PATH))) .setAccessType("offline") // Ensures we receive
	 * a refresh token .setApprovalPrompt("force") // Use 'force' if you want to
	 * re-prompt consent each time .build();
	 * 
	 * // LocalServerReceiver to handle OAuth callback after user consents (run on
	 * port 8888) LocalServerReceiver receiver = new
	 * LocalServerReceiver.Builder().setPort(8888).build();
	 * 
	 * // Obtain the credential object, which handles access tokens and refresh
	 * tokens Credential credential = new AuthorizationCodeInstalledApp(flow,
	 * receiver).authorize("user");
	 * 
	 * // Check if the access token has expired and refresh if needed if
	 * (credential.getExpiresInSeconds() != null && credential.getExpiresInSeconds()
	 * <= 0) { boolean refreshed = credential.refreshToken(); if (!refreshed) {
	 * throw new Exception("Failed to refresh token."); } }
	 * 
	 * return credential; }
	 */

	public void syncEventToGoogleCalendar(Integer userId, Integer eventId, String title, String description,
			OffsetDateTime startDateTime, OffsetDateTime endDateTime) throws Exception {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
				.setApplicationName(APPLICATION_NAME).build();

		Event event = new Event().setSummary(title).setDescription(description);

		// Convert OffsetDateTime to ZonedDateTime in IST (Asia/Kolkata)
		ZonedDateTime startDateTimeIST = startDateTime.atZoneSameInstant(ZoneId.of("Asia/Kolkata"));
		ZonedDateTime endDateTimeIST = endDateTime.atZoneSameInstant(ZoneId.of("Asia/Kolkata"));

		// Format the ZonedDateTime to ISO 8601 format with time zone offset
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
		String startDateTimeString = startDateTimeIST.format(formatter);
		String endDateTimeString = endDateTimeIST.format(formatter);

		// Convert the formatted strings to Google API DateTime
		DateTime start = new DateTime(startDateTimeString);
		EventDateTime startEventDateTime = new EventDateTime().setDateTime(start).setTimeZone("Asia/Kolkata");

		DateTime end = new DateTime(endDateTimeString);
		EventDateTime endEventDateTime = new EventDateTime().setDateTime(end).setTimeZone("Asia/Kolkata");

		event.setStart(startEventDateTime);
		event.setEnd(endEventDateTime);

		service.events().insert("primary", event).execute();

		UserCalendar calendar = new UserCalendar();
		calendar.setUserId(userId);
		calendar.setEventId(eventId);
		calendar.setStatus(true);

		calendarRepository.save(calendar);

	}
}
