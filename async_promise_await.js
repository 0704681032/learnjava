
//https://jakearchibald.com/2014/es7-async-functions/


function loadStory() {
  return getJSON('story.json').then(function(story) {
    addHtmlToPage(story.heading);

    return story.chapterURLs.map(getJSON)
      .reduce(function(chain, chapterPromise) {
        return chain.then(function() {
          return chapterPromise;
        }).then(function(chapter) {
          addHtmlToPage(chapter.html);
        });
      }, Promise.resolve());
  }).then(function() {
    addTextToPage("All done");
  }).catch(function(err) {
    addTextToPage("Argh, broken: " + err.message);
  }).then(function() {
    document.querySelector('.spinner').style.display = 'none';
  });
}

async function loadStory() {
  try {
    let story = await getJSON('story.json');
    addHtmlToPage(story.heading);
    for (let chapter of story.chapterURLs.map(getJSON)) {
      addHtmlToPage((await chapter).html);
    }
    addTextToPage("All done");
  } catch (err) {
    addTextToPage("Argh, broken: " + err.message);
  }
  document.querySelector('.spinner').style.display = 'none';
}

function loadStory() {
  return spawn(function *() {
    try {
      let story = yield getJSON('story.json');
      addHtmlToPage(story.heading);
      for (let chapter of story.chapterURLs.map(getJSON)) {
        addHtmlToPage((yield chapter).html);
      }
      addTextToPage("All done");
    } catch (err) {
      addTextToPage("Argh, broken: " + err.message);
    }
    document.querySelector('.spinner').style.display = 'none';
  });
}

